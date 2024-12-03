<!-- 카카오 지도 API 스크립트 -->
var mapContainer = document.getElementById('map'); // 지도를 표시할 div
var map; // 지도 객체
var markers = []; // 표시된 마커 배열
var userMarker; // 사용자 위치 마커
var geocoder = new kakao.maps.services.Geocoder(); // Geocoder 객체 생성
var places = new kakao.maps.services.Places(); // 장소 검색 객체 생성
var customOverlay; // 커스텀 오버레이 객체

// 지도 초기화 함수
function initializeMap(lat, lng) {
    var mapOption = {
    center: new kakao.maps.LatLng(lat, lng), // 중심 좌표
    level: 3 // 확대 레벨
    };
    map = new kakao.maps.Map(mapContainer, mapOption);

    // 사용자 위치에 빨간색 마커 표시
    userMarker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(lat, lng),
    map: map,
    image: new kakao.maps.MarkerImage('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', new kakao.maps.Size(50, 50),
    { offset: new kakao.maps.Point(25, 50) }
        )
    });

    // 주변 마트 검색
    searchNearbyMarts(lat, lng);

        // 지도 클릭 이벤트 등록
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
        if (customOverlay) customOverlay.setMap(null); // 커스텀 오버레이 닫기
    });

        // 지도 이동 이벤트 등록
    kakao.maps.event.addListener(map, 'dragend', function() {
        var center = map.getCenter(); // 이동 후 중심 좌표
        searchNearbyMarts(center.getLat(), center.getLng());
    });
}

// 주변 마트 검색 함수
function searchNearbyMarts(lat, lng) {
    clearMarkers(); // 기존 마커 제거
    var callback = function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            for (var i = 0; i < result.length; i++) {
                displayMarker(result[i]);
            }
        }
    };
    places.keywordSearch('마트', callback, {
    location: new kakao.maps.LatLng(lat, lng),
    radius: 500 // 500미터 반경 내 검색
    });
}

// 마커 표시 함수
function displayMarker(place) {
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x)
    });

    markers.push(marker); // 마커 배열에 추가

    // 마커 클릭 이벤트 등록
    kakao.maps.event.addListener(marker, 'click', function() {
        if (customOverlay) customOverlay.setMap(null); // 기존 오버레이 제거

        // 커스텀 오버레이 내용 생성
        var content = `
                        <div class="custom-overlay">
                            <div class="title">${place.place_name}</div>
                            <div>주소: ${place.address_name}</div>
                            <div>${place.phone ? '전화번호: ' + place.phone : '전화번호 없음'}</div>
                        </div>
                    `;

        // 커스텀 오버레이 생성
        customOverlay = new kakao.maps.CustomOverlay({
            content: content,
            map: map,
            position: marker.getPosition(),
            yAnchor: 1.5
        });
    });
}

// 기존 마커 제거 함수
function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 지도 타입 변경 함수
function setMapType(maptype) {
    var roadmapControl = document.getElementById('btnRoadmap');
    var skyviewControl = document.getElementById('btnSkyview');
    if (maptype === 'roadmap') {
        map.setMapTypeId(kakao.maps.MapTypeId.ROADMAP);
        roadmapControl.className = 'selected_btn';
        skyviewControl.className = 'btn';
    } else {
        map.setMapTypeId(kakao.maps.MapTypeId.HYBRID);
        skyviewControl.className = 'selected_btn';
        roadmapControl.className = 'btn';
    }
}

// 지도 확대/축소 함수
function zoomIn() {
    map.setLevel(map.getLevel() - 1);
}

function zoomOut() {
    map.setLevel(map.getLevel() + 1);
}

// 기본 주소 가져오기
function fetchDefaultAddress() {
    fetch('/map/defaultAddress')
        .then(response => {
            if (!response.ok) throw new Error('기본 주소를 가져오지 못했습니다.');
            return response.json();
        })
        .then(data => {
            if (data.address) {
                geocoder.addressSearch(data.address, function(result, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                        initializeMap(coords.getLat(), coords.getLng());
                    } else {
                        alert('기본 주소를 찾을 수 없습니다. GPS를 사용합니다.');
                        useGPSLocation();
                    }
                });
            } else {
                useGPSLocation();
            }
        })
        .catch(() => useGPSLocation());
}

// GPS를 사용한 지도 초기화
function useGPSLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
        initializeMap(position.coords.latitude, position.coords.longitude);
    }, () => alert('GPS 정보를 가져올 수 없습니다.'));
    } else {
        alert('브라우저가 위치 정보를 지원하지 않습니다.');
    }
}

// 실행: 기본 주소로 지도 초기화
fetchDefaultAddress();
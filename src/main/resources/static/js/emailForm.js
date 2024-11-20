$(document).ready(function() {
    $('#sendMemoButton').click(function() {
        // 로딩 오버레이 표시
        $('.loading-overlay').show();

        // 모든 notepad-line 요소를 가져오기
        let memoList = [];
        $('.notepad-line').each(function() {
            const lineText = $(this).text().trim();
            if (lineText !== '') { // 빈 줄은 제외
                memoList.push(lineText);
            }
        });

        // 메모 내용을 /emails/send로 전송
        $.ajax({
            url: '/emails/send',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ memo: memoList }),
            success: function(response) {
                $('.loading-overlay').attr('style', 'display: none !important;'); // 인라인 스타일로 설정
                Swal.fire({
                    icon: 'success',
                    title: '성공',
                    text: '메모 전송에 성공했습니다!',
                });
            },
            error: function(xhr, status, error) {
                console.error("에러 발생:", error);
                $('.loading-overlay').attr('style', 'display: none !important;'); // 인라인 스타일로 설정
                Swal.fire({
                    icon: 'error',
                    title: '실패',
                    text: '메모 전송에 실패했습니다',
                });
            },
        });
    });
});

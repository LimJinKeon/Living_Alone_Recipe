// 재료 삭제
function deleteIngredient(ingredientId) {
    Swal.fire({
        title: '냉장고에서 재료 빼기',
        text: '재료를 삭제하시겠습니까?',
        icon: 'warning',

        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소',
    }).then(result => {
        if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
            fetch(`/refrigerator/ingredients/${ingredientId}/delete`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    console.log(ingredientId + " 삭제");
                    // 페이지를 새로 고침
                    location.reload();
                }
            }).catch(error => {
                console.error("Error:", error);
                Swal.fire({
                    title: '오류',
                    text: '삭제 중 오류가 발생했습니다',
                    icon: 'warning',
                });
            })
        }
    });
}

// 수량 증가 요청 처리
function incrementQuantity(ingredientId) {
    fetch(`/refrigerator/ingredients/${ingredientId}/increment`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            console.log(ingredientId + " 수량 증가");
            location.reload();
        }
    }).catch(error => {
        console.error("Error:", error);
        Swal.fire({
            title: '오류',
            text: '수량 증가 중 오류가 발생했습니다',
            icon: 'warning',
        });
    })
}

// 수량 감소 요청 처리
function decrementQuantity(ingredientId) {
    fetch(`/refrigerator/ingredients/${ingredientId}/decrement`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            console.log(ingredientId + "수량 감소");
            location.reload();
        }
    }).catch(error => {
        console.error("Error:", error);
        Swal.fire({
            title: '오류',
            text: '수량 증가 중 오류가 발생했습니다',
            icon: 'warning',
        });
    })
}
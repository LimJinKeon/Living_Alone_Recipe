document.getElementById("user-input").addEventListener("keydown", function (event) {
    if (event.key === "Enter") { // Enter 키 감지
        event.preventDefault();
        addMessage();
    }
});

function addMessage() {
    const userInputField = document.getElementById("user-input");
    const userMessage = userInputField.value.trim();
    if (!userMessage) return; // 빈 입력은 무시

    const chatBox = document.getElementById("chat-box");

    // 사용자 메시지를 화면에 추가
    const messageDiv = document.createElement("div");
    messageDiv.className = "user-message";
    messageDiv.textContent = userMessage;
    chatBox.appendChild(messageDiv);
    chatBox.scrollTop = chatBox.scrollHeight; // 자동 스크롤
    userInputField.value = ""; // 입력창 비우기

    // 챗봇에게 메시지 전송
    sendMessage(userMessage);
}

function sendMessage(userMessage) {
    const chatBox = document.getElementById("chat-box");

    // 봇 메시지를 받을 div 생성
    const messageDiv = document.createElement("div");
    messageDiv.className = "bot-message";
    // 타이핑 효과 함수 호출
    typeMessage("답변을 기다리는 중...", messageDiv);
    chatBox.appendChild(messageDiv);
    chatBox.scrollTop = chatBox.scrollHeight;

    // 서버에 사용자 메시지 전송
    fetch("/api/chat/message", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ message: userMessage })
    })
        .then(response => response.json())
        .then(botResponse => {
            // 초기값 지우기
            messageDiv.textContent="";

            // 봇의 실제 응답으로 업데이트 및 줄바꿈 처리
            let botMessage = botResponse.response;

            // 타이핑 효과 함수 호출
            typeMessage(botMessage, messageDiv);

            // 자동 스크롤
            chatBox.scrollTop = chatBox.scrollHeight;
        })
        .catch(error => {
            console.error("Error:", error);
            messageDiv.textContent = "응답값을 받아올 수 없습니다. 네트워크를 확인해주세요.";
            chatBox.scrollTop = chatBox.scrollHeight;
        });
}

function typeMessage(message, element, index = 0) {
    if (index < message.length) {
        // 현재 글자가 줄바꿈 문자 (\n)인지 확인
        if (message.charAt(index) === "\n") {
            element.innerHTML += "<br>"; // 줄바꿈 문자일 경우 <br> 추가
        } else {
            element.innerHTML += message.charAt(index); // 일반 문자일 경우 해당 문자 추가
        }

        setTimeout(() => {
            typeMessage(message, element, index + 1);
        }, 50); // 타이핑 속도 조절 (50ms 간격으로 다음 문자 출력)

        // 스크롤을 메시지 입력에 맞춰서 아래로 이동시킵니다.
        const chatBox = document.getElementById("chat-box");
        chatBox.scrollTop = chatBox.scrollHeight;
    }
}
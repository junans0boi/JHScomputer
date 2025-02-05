// youtubeAPI.js

document.addEventListener('DOMContentLoaded', () => {
    fetchVideos();
});

let allVideos = [];   // 전역 변수에 전체 영상을 저장
let currentPage = 1;
const videosPerPage = 15;

async function fetchVideos() {
    try {
        // youtubeAPI.html은 100개 영상을 불러옴
        const response = await fetch('/api/youtube/videos?limit=100');
        if (!response.ok) {
            throw new Error('서버에서 동영상 데이터를 가져오지 못했습니다.');
        }
        allVideos = await response.json();
        // 최초 페이지 렌더링
        renderPage(1);
        setupPaginationControls();
    } catch (error) {
        console.error("동영상 가져오기 중 오류 발생:", error);
    }
}

function renderPage(page) {
    currentPage = page;
    const start = (page - 1) * videosPerPage;
    const end = start + videosPerPage;
    const videosForPage = allVideos.slice(start, end);
    
    const videosContainer = document.getElementById("videos");
    videosContainer.innerHTML = "";

    // 그리드 레이아웃 (CSS에서 .video 클래스와 부모 컨테이너에 grid 설정 필요)
    videosForPage.forEach((video) => {
        const videoElement = document.createElement('div');
        videoElement.classList.add('video');
        videoElement.innerHTML = `
            <a href="https://www.youtube.com/watch?v=${video.videoId}" target="_blank" rel="noopener noreferrer">
                <img src="${video.thumbnailUrl}" alt="${video.title} 썸네일">
                <h3>${video.title}</h3>
            </a>
        `;
        videosContainer.appendChild(videoElement);
    });
}

function setupPaginationControls() {
    // 페이지네이션 컨트롤을 표시할 요소 (예: id="pagination"를 가진 div)
    const paginationContainer = document.getElementById("pagination");
    if (!paginationContainer) return;

    paginationContainer.innerHTML = "";
    const totalPages = Math.ceil(allVideos.length / videosPerPage);

    // 이전 버튼
    const prevButton = document.createElement('button');
    prevButton.innerText = "이전";
    prevButton.disabled = currentPage === 1;
    prevButton.addEventListener('click', () => {
        if (currentPage > 1) renderPage(currentPage - 1);
        updatePaginationButtons();
    });
    paginationContainer.appendChild(prevButton);

    // 페이지 번호 버튼
    for (let i = 1; i <= totalPages; i++) {
        const pageButton = document.createElement('button');
        pageButton.innerText = i;
        if (i === currentPage) {
            pageButton.disabled = true;
        }
        pageButton.addEventListener('click', () => {
            renderPage(i);
            updatePaginationButtons();
        });
        paginationContainer.appendChild(pageButton);
    }

    // 다음 버튼
    const nextButton = document.createElement('button');
    nextButton.innerText = "다음";
    nextButton.disabled = currentPage === totalPages;
    nextButton.addEventListener('click', () => {
        if (currentPage < totalPages) renderPage(currentPage + 1);
        updatePaginationButtons();
    });
    paginationContainer.appendChild(nextButton);
}

function updatePaginationButtons() {
    // 버튼 상태 업데이트 (이전/다음 활성화/비활성화 등)
    const paginationContainer = document.getElementById("pagination");
    const buttons = paginationContainer.querySelectorAll("button");
    const totalPages = Math.ceil(allVideos.length / videosPerPage);
    buttons.forEach(btn => {
        if (btn.innerText === "이전") {
            btn.disabled = currentPage === 1;
        } else if (btn.innerText === "다음") {
            btn.disabled = currentPage === totalPages;
        } else {
            btn.disabled = parseInt(btn.innerText) === currentPage;
        }
    });
}
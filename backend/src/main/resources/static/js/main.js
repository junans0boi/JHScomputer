// // main.js

// document.addEventListener('DOMContentLoaded', () => {
//     loadNavbar();
// });

// async function loadNavbar() {
//     const navbarContainer = document.getElementById('navbar');
//     try {
//         const response = await fetch('navbar.html');
//         if (!response.ok) {
//             throw new Error('네비게이션 바를 불러오는데 실패했습니다.');
//         }
//         const navbarHtml = await response.text();
//         navbarContainer.innerHTML = navbarHtml;
//     } catch (error) {
//         console.error(error);
//     }
// }

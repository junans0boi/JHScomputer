// login.js

document.addEventListener('DOMContentLoaded', () => {
    const loginButton = document.querySelector('.login-btn');
    const inputs = document.querySelectorAll('#login-form .input-field');

    function checkInputs() {
        const allFilled = Array.from(inputs).every(input => input.value.trim() !== '');
        if (allFilled) {
            loginButton.classList.add('active');
            loginButton.disabled = false;
        } else {
            loginButton.classList.remove('active');
            loginButton.disabled = true;
        }
    }

    inputs.forEach(input => {
        input.addEventListener('input', checkInputs);
    });

    // Initial check in case fields are pre-filled
    checkInputs();
});

// signup.js

document.addEventListener('DOMContentLoaded', () => {
    const signupButton = document.querySelector('.register-submit-btn');
    const signupInputs = document.querySelectorAll('#signup-form .input-field');
    const emailInput = document.getElementById('signup-email');
    const passwordInput = document.getElementById('signup-password');
    const passwordConfirmInput = document.getElementById('signup-password-confirm');
    const phoneNumInput = document.getElementById('signup-phoneNum');

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    function checkSignupInputs() {
        const allFilled = Array.from(signupInputs).every(input => input.value.trim() !== '');
        const emailValid = emailRegex.test(emailInput.value);
        const passwordsMatch = (passwordInput.value === passwordConfirmInput.value);

        if (allFilled && emailValid && passwordsMatch) {
            signupButton.classList.add('active');
            signupButton.disabled = false;
        } else {
            signupButton.classList.remove('active');
            signupButton.disabled = true;
        }
    }

    signupInputs.forEach(input => {
        input.addEventListener('input', checkSignupInputs);
    });

    checkSignupInputs();

    // 전화번호 자동 포맷팅
    window.formatPhoneNumber = function (input) {
        let value = input.value.replace(/\D/g, ''); // 숫자 외 문자 제거

        if (value.startsWith('010')) {
            if (value.length > 3 && value.length <= 7) {
                value = value.replace(/(\d{3})(\d+)/, '$1-$2');
            } else if (value.length > 7) {
                value = value.replace(/(\d{3})(\d{4})(\d+)/, '$1-$2-$3');
            }
        }

        input.value = value;
    };
});
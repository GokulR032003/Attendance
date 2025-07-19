// script.js

document.addEventListener("DOMContentLoaded", function () {
  const loginForm = document.querySelector("form");

  if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
      const username = document.getElementById("username");
      const password = document.getElementById("password");

      if (!username.value || !password.value) {
        e.preventDefault();
        alert("Please enter both username and password.");
      }
    });
  }

  // Additional interactive behaviors can be added here
});

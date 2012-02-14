
function MainController($route, $xhr, userManager) {

	this.userManager = userManager;
	this.$xhr = $xhr;
	$xhr.defaults.headers.put["Content-Type"] = "application/json";
	$xhr.defaults.headers.post["Content-Type"] = "application/json";
	$xhr.defaults.headers.common["Content-Type"] = "application/json";
	
	this.checkLoggedIn();	
}

MainController.$inject = ["$route", "$xhr", "userManager" ];

MainController.prototype = {

		checkLoggedIn : function() {
			this.$xhr("GET", "loginDetails?" + new Date().getTime(), this.authenticationCallback);
		},
		
		login : function() {
			this.userManager.authenticating();
			this.$root.$eval(); // required to update view when pressing return, so that if still an error it is displayed
			if (this.userManager.credentials.username === "") {
				this.userManager.error = "You must supply a valid username.";
				this.userManager.unauthenticated();
				return;
			}
			this.$xhr("POST", "j_spring_security_check", this.userManager.credentials, this.authenticationCallback);
		},
		
		authenticationCallback : function(code, response) {
			if (response && response.username) {
				this.userManager.authenticated(response);
			} else if (code === 403) {
				this.userManager.unauthenticated("Unknown username and/or password");
			} else {
				this.userManager.unauthenticated();
			}
		},
		
		logout : function() {
			this.userManager.unauthenticated();
			this.$xhr("GET", "j_spring_security_logout", angular.noop);
		},
		
};

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
			this.$xhr("GET", "/ws/auth/user?" + new Date().getTime(), this.authenticationCallback);
		},
		
		authenticationCallback : function(code, response) {
			if (response && response.username) {
				this.userManager.authenticated(response);
			} else {
				this.userManager.unauthenticated();
			}
		},
		
		logout : function() {
			this.userManager.unauthenticated();
			this.$xhr("GET", "/logout", angular.noop);
		},
		
};
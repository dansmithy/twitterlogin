angular.service('$xhr.error', function(){
	  return function(request, response){
		  request.callback(response.status, response.body);
	  };
	}, {
		$eager : 'true'
	});

angular.service("userManager", function() {
	return {
		
		credentials : {},
		user : undefined,
		state : "verifying", // also unauthenticated, authenticated, authenticating
		error : undefined,
		
		authenticated : function(newUser) {
			this.user = newUser;
			this.state = "authenticated";
			this.credentials.password = undefined;
		},
		
		authenticating : function() {
			this.error = undefined;
			this.state = "authenticating";
		},
		
		unauthenticated : function(error) {
			if (error) {
				this.error = error;
			}
			this.user = undefined;
			this.state = "unauthenticated";
		},
		
		isAuthenticated : function() {
			return this.state == "authenticated";
		},
		
		isUnauthenticated : function() {
			return this.state == "unauthenticated";
		},
		
		isAuthenticating : function() {
			return this.state == "authenticating";
		},
		
		isVerifying : function() {
			return this.state == "verifying";
		},
		
		isVerifiedButUnauthenticated : function() {
			return this.isUnauthenticated() || this.isAuthenticating();
		},
		
		isProcessing : function() {
			return this.isAuthenticating() || this.isVerifying();
		},
		
		hasErrors : function() {
			return angular.isDefined(this.error);
		}
		
	};
	
});



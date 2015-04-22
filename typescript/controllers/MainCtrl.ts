interface IMainCtrl {
    secureCookie: SecureCookie;
    getWelcome:() => string;
}


module Controllers {
    export class MainCtrl implements IMainCtrl {
        secureCookie:SecureCookie;

        getWelcome() {
            if (this.secureCookie.isLoggedIn()) {
                return "Hello there, " + this.secureCookie.getName()
            } else {
                return "Hello there, please login";
            }
        }

        constructor($scope, SecureCookie) {
            this.secureCookie = SecureCookie;
            $scope.vm = this;
        }
    }
}

module Controllers{
  export class MainController {
   message = "Welcome Soldier!";
   constructor($scope){
     $scope.vm = this;
   }
  }
}

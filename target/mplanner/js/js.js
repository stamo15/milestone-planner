$(document).ready(function(){
    $('.tabs').tabs();
    $(".dropdown-trigger").dropdown({
     constrainWidth: false
    });
    
    $('.modal').modal();
    $(document).ready(function(){
        $('select').formSelect();
    });
});
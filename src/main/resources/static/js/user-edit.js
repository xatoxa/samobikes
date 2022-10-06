function checkUsername(form){
        url = "[[@{/users/check_username}]]";
        username = $("#username").val();
        userId = $("#id").val();
        csrfValue = $("input[name='_csrf']").val();
        params = {id: userId, username: username, _csrf: csrfValue};
        $.post(url, params, function(response){
            if(response == "OK"){
                form.submit();
            } else if(response == "Duplicated"){
                alert("Есть другой пользователь с логином " + username);
            } else {
                alert("Произошло что-то непонятное...");
            }
        }).fail(function(){
            alert("Невозможно подключиться к серверу.");
        });

        return false;
    }
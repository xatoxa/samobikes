function checkInfo(form){
        url = "[[@{/bikes/check_info}]]";
        number = $("#number").val();
        qrNumber = $("#qrNumber").val();
        VIN = $("#VIN").val();
        bikeId = $("#id").val();
        csrfValue = $("input[name='_csrf']").val();
        params = {id: bikeId, number: number, qrNumber: qrNumber, VIN: VIN, _csrf: csrfValue};
        $.post(url, params, function(response){
            if(response == "111"){
                form.submit();
            } else if(response == "001"){
                alert("Есть другой велосипед с номером: " + number + " и QR номером: " + qrNumber);
            } else if(response == "010"){
                alert("Есть другой велосипед с номером: " + number + " и VIN: " + VIN);
            } else if(response == "011"){
                alert("Есть другой велосипед с номером: " + number);
            } else if(response == "100"){
                alert("Есть другой велосипед с QR номером: " + qrNumber + " и VIN: " + VIN);
            } else if(response == "101"){
                alert("Есть другой велосипед с QR номером: " + qrNumber);
            } else if(response == "110"){
                alert("Есть другой велосипед с VIN: " + VIN);
            } else if(response == "000"){
                alert("Есть другой велосипед с номером: " + number + ", QR номером: " + qrNumber+ " и VIN: " + VIN);
            } else {
                alert("Произошло что-то непонятное...");
            }
        }).fail(function(){
            alert("Невозможно подключиться к серверу.");
        });

        return false;
    }
    $(document).ready(function() {
            $("#fileImage").change(function() {
                fileSize = this.files[0].size;
                if (fileSize > 1048567) {
                    this.setCustomValidity("Файл должен быть меньше 1 МБ");
                    this.reportValidity();
                } else {
                    this.setCustomValidity("");
                    showImageThumbnail(this);
                }
            });
        });

    function showImageThumbnail(fileInput){
                var file = fileInput.files[0];
                var reader = new FileReader();
                reader.onload = function(e) {
                    $("#thumbnail").attr("src", e.target.result);
                };
                reader.readAsDataURL(file);
            }

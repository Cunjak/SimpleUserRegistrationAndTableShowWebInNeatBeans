  $(document).on("submit", "#myForm", function (event) {
                var $form = $(this);

                $.post("Register", $form.serialize(), function (response) {

                    if (response.userList.length == 0)
                        $("#UserTable").html("");
                    else
                    {
                        if ($("#UserTable").has("table").length == 0)
                        {
                            var $table = $("<table class='mx-auto table table-striped table-responsive-sm'>").appendTo($("#UserTable"));
                            $table.html("<thead><tr><th>Full Name</th><th>Address</th><th>City</th><th>Country</th><th>Email</th><th>Action</th></tr></thead><tbody></tbody>");
                        } else
                            $("tbody").html("");

                        var i;
                        for (i = 0; i < response.userList.length; i++) {
                            var $tr = $("<tr>").appendTo($("tbody"));
                            $("<td>").text(response.userList[i].firstName + " " + response.userList[i].lastName).appendTo($tr);
                            $("<td>").text(response.userList[i].address).appendTo($tr);
                            $("<td>").text(response.userList[i].city).appendTo($tr);
                            $("<td>").text(response.userList[i].country).appendTo($tr);
                            $("<td>").text(response.userList[i].email).appendTo($tr);
                            $("<td>").html('<a class="delete" onclick="deleteFunction(this)"  data-email="' + response.userList[i].email + '" href="#">Delete</a>').appendTo($tr);
                        }
                        ;
                    }

                    switch (response.validation) {
                        case "FirstNameEmpty":
                            if ($('input[name = "FirstName"]').parent().has("label").length == 0)
                                $('input[name = "FirstName"]').after("<label>This field is required</label>");
                            return;
                        case "LastNameEmpty":
                            if ($('input[name = "LastName"]').parent().has("label").length == 0)
                                $('input[name = "LastName"]').after("<label>This field is required</label>");
                            return;
                        case "AddressEmpty":
                            if ($('input[name = "Address"]').parent().has("label").length == 0)
                                $('input[name = "Address"]').after("<label>This field is required</label>");
                            return;
                        case "CityEmpty":
                            if ($('input[name = "City"]').parent().has("label").length == 0)
                                $('input[name = "City"]').after("<label>This field is required</label>");
                            return;
                        case "CountryEmpty":
                            if ($('input[name = "Country"]').parent().has("label").length == 0)
                                $('input[name = "Country"]').after("<label>This field is required</label>");
                            return;
                        case "EmailEmpty":
                            if ($('input[name = "Email"]').parent().has("label").length == 0)
                                $('input[name = "Email"]').after("<label>This field is required</label>");
                            return;
                        case "PasswordEmpty":
                            if ($('input[name = "Password"]').parent().has("label").length == 0)
                                $('input[name = "Password"]').after("<label>This field is required</label>");
                            return;
                        case "WrongCountry":
                            if ($('input[name = "Country"]').parent().has("label").length == 0)
                                $('input[name = "Country"]').after("<label>This country doesnt exist</label>");
                            return;
                        case "EmailExists":
                            if ($('input[name = "Email"]').parent().has("label").length == 0)
                                $('input[name = "Email"]').after("<label>Email already exists</label>");
                            return;
                        default:
                    }
           
                    $('input[name = "FirstName"]').val("");
                    $('input[name = "LastName"]').val("");
                    $('input[name = "Address"]').val("");
                    $('input[name = "City"]').val("");
                    $('input[name = "Country"]').val("");
                    $('input[name = "Email"]').val("");
                    $('input[name = "Password"]').val("");
                });

                event.preventDefault(); // Important! Prevents submitting the form.
            });




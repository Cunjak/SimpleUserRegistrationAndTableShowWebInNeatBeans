function deleteFunction(element)
{
    $.post("Delete", {"email": element.getAttribute("data-email")}, function (response)
    {
        $("#myTable").html(response);
    });
}
;



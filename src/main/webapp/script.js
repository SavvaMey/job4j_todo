function saveItem() {
    if (validate()) {
        $.ajax({
            type: 'POST',
            crossdomain: true,
            url: 'http://localhost:8080/TODO/index',
            data: {description: $('#newTask').val()},
            dataType: 'json',
        }).done(function (data) {
            let addTr = '<tr>'
                + '<td>' + data.idTask + '</td>'
                + '<td>' + data.description + '</td>'
                + '<td>' + data.creatDate + '</td>';
            addTr += '<td>' + '<input type="checkbox" name="done" id="' + data.idTask + '" onchange = "finishTask(this.id)"'
                + 'value="' + data.idTask + '">' + '</td>' + '</tr>';
            $('#table tr:last').after(addTr);
        }).fail(function (err) {
            alert(err);
        });
    }
}

function loadAll() {
    $('#table tbody').empty();
    $.ajax({
        type: 'GET',
        crossdomain: true,
        dataType: 'json',
        url: 'http://localhost:8080/TODO/index',
    }).done(function (data) {
        let addTr = '';
        $.each(data, function (i, item) {
            addTr += '<tr>'
                + '<td>' + item.idTask + '</td>'
                + '<td>' + item.description + '</td>'
                + '<td>' + item.creatDate + '</td>';
            if (item.finished !== true) {
                addTr += '<td>' + '<input type="checkbox" name="done" id="' + item.idTask + '" onchange = "finishTask(this.id)"'
                    + 'value="' + item.idTask + '">' + '</td>' + '</tr>';
            } else {
                addTr += '<td>' + '<p>finished</p>' + '</td>' + '</tr>';
            }
        });
        $('#TableBody').append(addTr);
    }).fail(function (err) {
        alert(err);
    });
}

function viewTasks() {
    $('#table tbody').empty();
    $.ajax({
        type: 'GET',
        crossdomain: true,
        dataType: 'json',
        url: 'http://localhost:8080/TODO/index',
    }).done(function (data) {
        let addTr = '';
        var element = $('input[type="checkbox"]');
        if (element.is(':checked')) {
            loadAll();
        } else {
            $.each(data, function (i, item) {
                if (item.finished !== true) {
                    addTr += '<tr>'
                        + '<td>' + item.idTask + '</td>'
                        + '<td>' + item.description + '</td>'
                        + '<td>' + item.creatDate + '</td>';
                    addTr += '<td>' + '<input type="checkbox" name="done" id="' + item.idTask + '" onchange = "finishTask(this.id)"'
                        + 'value="' + item.idTask + '">' + '</td>' + '</tr>';
                }
            });
        }
        $('#TableBody').append(addTr);
    }).fail(function (err) {
        alert(err);
    });
}

$(document).ready(
    loadAll()
);


function finishTask(id) {
    $("#" + id).attr('disabled', !$("#" + id).attr('disabled'));
    $.ajax({
        type: 'POST',
        crossdomain: true,
        url: 'http://localhost:8080/TODO/index',
        data: {idTask: id},
    }).done(
        alert("task №" + id + " is finished"));
}


function validate() {
    let result = true;
    let task = $('#newTask').val();
    if (task === '') {
        alert('enter description')
        result = false;
    }
    return result;
}

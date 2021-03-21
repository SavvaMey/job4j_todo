function saveItem() {
    if (validate()) {
        console.log($("#category").val());
        console.log(JSON.stringify($("#category").val()));
        $.ajax({
            type: 'POST',
            crossdomain: true,
            url: 'http://localhost:8080/TODO/index',
            data: ({description: $('#newTask').val(),
            categories: $("#category").val()}),
            dataType: 'json',
        }).done(function (data) {
            alert(data.userName);
            let addTr = '<tr>'
                + '<td>' + data.idTask + '</td>'
                + '<td>' + data.description + '</td>'
                + '<td>' + data.creatDate + '</td>'
                + '<td>' + data.userName + '</td>';
            addTr += '<td>' + '<ul>'
            for (let k = 0; k < data.categorories.length; k++) {
                addTr += '<li>' + data.categorories[k] + '</li>'
            }
            addTr += '</ul>' + '</td>'
            addTr += '<td>' + '<input type="checkbox" name="done" id="' + data.idTask + '" onchange = "finishTask(this.id)"'
                + 'value="' + data.idTask + '">' + '</td>' + '</tr>';
            $('#table tr:last').after(addTr);
        }).fail(function (err) {
            alert("ошибка с сохранением таски");
        });
    }
}

function loadAll() {
    $('#table tbody').empty();
    $.ajax({
        type: 'GET',
        crossdomain: true,
        dataType: 'json',
        contentType: "text/json;charset=utf-8",
        url: 'http://localhost:8080/TODO/index',
    }).done(function (data) {
        let addTr = '';
        $.each(data, function (i, item) {
            addTr += '<tr>'
                + '<td>' + item.idTask + '</td>'
                + '<td>' + item.description + '</td>'
                + '<td>' + item.creatDate + '</td>'
                + '<td>' + item.userName + '</td>';
            addTr += '<td>' + '<ul>'
            for (let k = 0; k < item.categorories.length; k++) {
                addTr += '<li>' + item.categorories[k] + '</li>'
            }
            addTr += '</ul>' + '</td>'
            if (item.finished !== true) {
                addTr += '<td>' + '<input type="checkbox" name="done" id="' + item.idTask + '" onchange = "finishTask(this.id)"'
                    + 'value="' + item.idTask + '">' + '</td>' + '</tr>';
            } else {
                addTr += '<td>' + '<p>finished</p>' + '</td>' + '</tr>';
            }
        });
        $('#TableBody').append(addTr);
    }).fail(function (err) {
        alert("не удалось загрузить таски");
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
                        + '<td>' + item.creatDate + '</td>'
                        + '<td>' + item.userName + '</td>';
                    addTr += '<td>' + '<ul>'
                    for (let k = 0; k < item.categorories.length; k++) {
                        addTr += '<li>' + item.categorories[k] + '</li>'
                    }
                    addTr += '</ul>' + '</td>'
                    addTr += '<td>' + '<input type="checkbox" name="done" id="' + item.idTask + '" onchange = "finishTask(this.id)"'
                        + 'value="' + item.idTask + '">' + '</td>' + '</tr>';
                }
            });
        }
        $('#TableBody').append(addTr);
    }).fail(function (err) {
        alert("не удалось загрузить таски");
    });
}

$(document).ready(function() {
        getCateg();
        loadAll();
        userView();
    }
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

function userView() {
    $.ajax({
        type: 'GET',
        crossdomain: true,
        url: 'http://localhost:8080/TODO/user',
        datatype: 'json',
    }).done(function (data) {
        $('#currentUser').append('current user : ' + data.user)
    });
}

function getCateg() {
    $.ajax({
        type: 'GET',
        crossdomain: true,
        url: 'http://localhost:8080/TODO/category',
    }).done(function (data) {
        let s = '<select class="form-control" id="category" name="category" multiple>';
        $.each(data, function (i, category) {
            s += '<option value="' + category.idCat + '">' + category.nameCat + '</option>';
        });
        s += '</select>';
        $('#categories').append(s);
    }).fail(function (err) {
        alert("category not load");
    });
}


function validate() {
    let result = true;
    let task = $('#newTask').val();
    let cat = $("#category").val();
    console.log($("#category").val());
    if (task === '' || cat.length === 0) {
        alert('enter description and choose categories')
        result = false;
    }
    return result;
}

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

    <!-- JavaScript -->
    <script src="static/js/main.js?ver=2"></script>

    <!-- Title -->
    <title>TODO list</title>
</head>
<body class="bg-light">
    <!-- Modal -->
    <div class="modal fade" id="edit_dialog" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="edit_dialog_title"></h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="form-group" id="groupId">
                            <label for="id">id</label>
                            <label class="form-control" type="text" id="id" readonly></label>
                        </div>
                        <div class="form-group">
                            <label for="textDescription">Описание</label>
                            <textarea class="form-control" id="textDescription" rows="5" placeholder="Введите описание" value=""></textarea>
                        </div>
                        <div class="form-group">
                            <label for="doneDate">Выполнить до</label>
                            <div class="row">
                                <div class="col">
                                    <input class="form-control" type="date" id="doneDate"/>
                                </div>
                                <div class="col">
                                    <input class="form-control" type="time" id="doneTime"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group pt-3" id="groupIsDone">
                            <div class="form-group form-check">
                                <input type="checkbox" class="form-check-input" id="isDone">
                                <label for="isDone">Выполнено</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="floatLeft btn btn-danger mr-auto" type="button" data-dismiss="modal" id="btnDelete">Удалить</button>
                    <button class="btn btn-secondary" type="button" id="btnClose" data-dismiss="modal">Закрыть</button>
                    <button class="btn btn-primary" type="button" id="btnSave">Сохранить</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid p-3">
        <button type="button" class="floatLeft btn btn-primary mr-auto" id="btnInsert">Новая запись</button>
        <button class="ml-3 btn btn-secondary dropdown-toggle" type="button" id="dropdownFilter" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Все записи
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownFilter">
            <a class="dropdown-item" href="#" data-select="all">Все записи</a>
            <a class="dropdown-item" href="#" data-select="closed">Только выполненные</a>
            <a class="dropdown-item" href="#" data-select="opened">Только не выполненные</a>
        </div>
    </div>
    <div class="container-fluid">
        <div class="my-4 p-3 bg-white rounded shadow-sm">
            <h6 class="border-bottom border-gray pb-3 mb-4">Список задач</h6>
            <table class="table table-hover" id="table">
                <thead class="thead-dark">
                    <tr class="row">
                        <th class="col-4">Описание</th>
                        <th class="col-2">Создана</th>
                        <th class="col-2">Последняя редакция</th>
                        <th class="col-2">Выполнить до</th>
                        <th class="col-2">Дата выполнения</th>
                    </tr>
                </thead>
                <tbody id="todo-list">
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
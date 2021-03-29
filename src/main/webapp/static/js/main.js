const get_json = function (params) {
    return $.getJSON(`./items`, params,
        function (data) {
            console.log(`get_json success`);
            return data;
        })
        .done(function () {
            console.log(`get_json second success`);
        })
        .fail(function () {
            console.log(`get_json fail`);
        })
        .always(function () {
            console.log(`get_json finished`);
        });
};

const post_item = function (params) {
    return $.post(`./items`, params,
        function success(data) {
            console.log(`post_item success`);
            return data;
        })
        .done(function () {
            console.log(`post_item second success`);
        })
        .fail(function () {
            console.log(`post_item fail`);
        })
        .always(function () {
            console.log(`post_item finished`);
        });
}

function renderTable(json) {
    $.each(json, function (key, value) {
        addRow(value);
    });
}

function clearTable() {
    $(`#todo-list`).empty();
}

function addRow(item) {
    const setDescription = function () {
        return item.description === undefined ? `` : item.description;
    }
    const setCreateTime = function () {
        return item.createTime === undefined ? `` : item.createTime;
    }
    const setEditTime = function () {
        return item.editTime === undefined ? `` : item.editTime;
    }
    const setDoneTime = function () {
        return item.doneTime === undefined ? `` : item.doneTime;
    }
    const setIsDone = function () {
        return item.isDone === 1 ?
            `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-check" viewBox="0 0 16 16"> ` +
            `<path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z"/> ` +
            `</svg> ${item.factDoneTime}` : ``;
    };
    let row = $(`#${item.id}`);
    if (row.length > 0) {
        row.find(`#description`).text(setDescription());
        row.find(`#editTime`).text(setEditTime());
        row.find(`#doneTime`).text(setDoneTime());
        row.find(`#isDone`).html(setIsDone());
    } else {
        $(`#todo-list`).append(
            `<tr class="row" id=${item.id}>` +
            `<td class="col-4" id="description">${setDescription()}</td>` +
            `<td class="col-2" id="createTime">${setCreateTime()}</td>` +
            `<td class="col-2" id="editTime">${setEditTime()}</td>` +
            `<td class="col-2" id="doneTime">${setDoneTime()}</td>` +
            `<td class="col-2" id="isDone">${setIsDone()}</td>` +
            `</tr>`
        );
    }
}

function dialogEditItem(id) {
    let row = $(`#${id}`);
    let dialog = $(`#edit_dialog`);

    // params
    dialog.find(`#edit_dialog_title`).text(`Редактирование`);
    dialog.find(`#id`).text(id);
    dialog.find(`#groupId`).show();
    dialog.find(`#textDescription`).val(row.find(`#description`).text());

    // done date time
    const dateTime = row.find(`#doneTime`).text().split(" ");
    dialog.find(`#doneDate`).val(dateTime[0]);
    dialog.find(`#doneTime`).val(dateTime[1]);

    // is done
    dialog.find(`#isDone`).prop(`checked`, function () {
        return row.find(`#isDone`).has(`svg`).length
    });
    dialog.find(`#groupIsDone`).show();

    // footer buttons
    dialog.find(`#btnDelete`).show();
    dialog.modal(`show`);
}

function dialogInsertItem() {
    let dialog = $(`#edit_dialog`);
    dialog.find(`#edit_dialog_title`).text(`Новая запись`);

    // params
    dialog.find(`#id`).text(``);
    dialog.find(`#groupId`).hide();
    dialog.find(`#textDescription`).val(``);

    // done date time
    const today = new Date();
    dialog.find(`#doneDate`).val(today.toISOString().substring(0, 10));
    dialog.find(`#doneTime`).val(today.toISOString().substr(11, 8));

    // is done
    dialog.find(`#isDone`).prop(`checked`, false);
    dialog.find(`#groupIsDone`).hide();

    // footer buttons
    dialog.find(`#btnDelete`).hide();
    dialog.modal(`show`);
}

$(document).ready(function () {
    // onClick button listener
    $(`#btnInsert`).on(`click`, function () {
        dialogInsertItem();
    });

    // onClick filter item listener
    $(`.dropdown-menu`).on(`click`, `.dropdown-item`, function() {
        $(`#dropdownFilter`).text($(this).html());
        $.when(
            get_json(
                {
                    filter: $(this).attr(`data-select`)
                })
        ).then(function (data) {
            clearTable();
            renderTable(data);
        });
    });

    // delegate onClick listener to all rows
    $(`#todo-list`).delegate(`tr`, `click`, function() {
        dialogEditItem($(this).attr(`id`))
    });

    // edit dialog
    let dialog = $(`#edit_dialog`);

    // onSave button listener
    $(`#btnSave`).on(`click`, function () {
        $.when(
            post_item(
                JSON.stringify(
                    {
                        action: "add",
                        id: dialog.find(`#id`).text() === `` ? undefined : dialog.find(`#id`).text(),
                        doneTime: dialog.find(`#doneDate`).val() + ` ` + dialog.find(`#doneTime`).val(),
                        description: dialog.find(`#textDescription`).val(),
                        isDone: dialog.find(`#isDone`).prop(`checked`) ? 1 : 0
                    })
            )
        ).then(function (data) {
            const item = JSON.parse(data);
            console.log(item);
            addRow(item);
            dialog.modal(`hide`);
        })
    });

    // onDelete button listener
    $(`#btnDelete`).on(`click`, function () {
        $.when(
            post_item(
                JSON.stringify(
                    {
                        action: "delete",
                        id: dialog.find(`#id`).text() === `` ? undefined : dialog.find(`#id`).text(),
                        doneTime: dialog.find(`#doneDate`).val() + ` ` + dialog.find(`#doneTime`).val(),
                        description: dialog.find(`#textDescription`).val(),
                        isDone: dialog.find(`#isDone`).prop(`checked`) ? 1 : 0
                    })
            )
        ).then(function (data) {
            const response = JSON.parse(data);
            console.log(response);
            if (response.result) {
                $(`#${response.id}`).empty();
                console.log(`item ${response.id} deleted`);
            } else {
                console.log(`item ${response.id} not deleted`);
            }
            dialog.modal(`hide`);
        })
    });

    // load to-do list
    $.when(
        get_json({
            filter: "all"
        })
    ).then(function (data) {
        renderTable(data);
    });

});

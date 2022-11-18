const tableBody = document.querySelector('#users-table-body')
const deleteModalForm = document.querySelector('#userDeleteForm')
const newUserForm = document.querySelector("#newUserForm")
const userEditForm = document.querySelector("#userEditForm")

fetch("/admin/api/users").then(response => response.json())
    .then(updateUsersTable)

function updateUsersTable(users) {
    tableBody.innerHTML = ""
    users.forEach((user) => {
        let newRow = document.createElement('tr')
        newRow["innerHTML"] = `
                  <td>${user.id}</td>
                  <td>${user.username}</td>
                  <td>${user.firstName}</td>
                  <td>${user.lastName}</td>
                  <td>${user.age}</td>
                  <td>${user.email}</td>
`
        let rolesCell = document.createElement('td')
        for (let roleId in user.roles) {
            let newSpan = document.createElement('span')
            newSpan.textContent = user.roles[roleId].name
            newSpan.dataset.roleId = user.roles[roleId].id
            if (roleId !== user.roles.length - 1) rolesCell.innerHTML += " "
            rolesCell.appendChild(newSpan)
        }
        rolesCell.id = "rolesCell_" + user.id
        newRow.appendChild(rolesCell)

        let editButtonCell = document.createElement('td')
        let editButtonLink = document.createElement('a')
        editButtonLink.innerHTML = `<a class=\"btn btn-info text-white btn-sm\" role=\"button\" data-toggle=\"modal\" data-target=\"#userEditModal\" data-userId=\"${user.id}\">Edit</a>`
        editButtonLink.onclick = (event) => openEditModal(event)
        editButtonCell.appendChild(editButtonLink)
        newRow.appendChild(editButtonCell)

        let deleteButtonCell = document.createElement('td')
        let deleteButtonLink = document.createElement('a')
        deleteButtonLink.innerHTML = `<a class=\"btn btn-danger text-white btn-sm\" role=\"button\" data-toggle=\"modal\" data-target=\"#userDeleteModal\" data-userId=\"${user.id}\">Delete</a>`
        deleteButtonLink.onclick = (event) => openDeleteModal(event)
        deleteButtonCell.appendChild(deleteButtonLink)
        newRow.appendChild(deleteButtonCell)
        tableBody.appendChild(newRow)
    })
}

function openEditModal(event) {
    let userId = event.target.dataset.userid
    fetch("/admin/api/users/" + userId).then(response => response.json())
        .then((data) => {
            updateModal(data, userEditForm)
        })
}

function openDeleteModal(event) {
    let userId = event.target.dataset.userid
    fetch("/admin/api/users/" + userId).then(response => response.json())
        .then((data) => {
            updateModal(data, deleteModalForm)
        })
}

function updateModal(user, form) {
    for (let key in user) {
        let inputField = form.querySelector(`input[name=\"${key}\"]`)
        if (inputField != null) inputField.value = user[key]
    }
    form.querySelectorAll("option").forEach((optionNode) => {
        if (user.roles.findIndex(element => element.id === Number(optionNode["value"])) >= 0) {
            optionNode.setAttribute("selected", true)
        } else {
            optionNode.removeAttribute("selected")
        }
    })
}

function sendUpdateRequest() {
    let user = createUserObject(userEditForm)
    fetch("/admin/api/users/", {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(
            {
                "user": user,
                "password": document.querySelector("#editModalPasswordField")["value"]
            })
    })
        .then(response => response.json()).then(updateUsersTable)
}

function sendDeleteRequest() {
    let userId = document.querySelector('#deleteModalIdField')["value"]
    fetch("/admin/api/users/", {
        method: "DELETE",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(
            {"id": userId})
    })
        .then(response => response.json()).then(updateUsersTable)
}

function sendCreateRequest() {
    let user = createUserObject(newUserForm)
    fetch("/admin/api/users/", {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(response => response.json()).then(updateUsersTable)
    document.querySelector("#l-admin-page").dispatchEvent(new MouseEvent("click"))
    newUserForm.reset()
}

function createUserObject(form) {
    let user = {};
    new FormData(form).forEach((value, key) => user[key] = value);
    user["roles"] = []
    form.querySelectorAll('select option').forEach((roleOption) => {
        if (roleOption["selected"]) {
            user["roles"].push({
                "id": roleOption["value"],
                "name": roleOption["innerText"]
            })
        }
    })
    return user
}

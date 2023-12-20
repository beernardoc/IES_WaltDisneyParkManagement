function CreateEmployee(name, email, password, role) {
    if (!name || !email || !password || !role) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const apiUrl = `/api/employee`;

    const formData = {
        Name: name,
        Email: email,
        Password: password,
        Role: role
    };

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Defina o tipo de conteúdo para formulário
        },
        body: JSON.stringify(formData)
    }).then(response => {
        if (response.ok) {
            window.location.reload();

        } else {
            alert("Erro ao criar funcionário.");
        }
    })
}

function UpdateEmployee(name, email, password, role) {

    if (!name || !email || !password || !role) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const apiUrl = `/api/employee`;

    const formData = {
        Name: name,
        Email: email,
        Password: password,
        Role: role
    };

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    }).then(response => {
        if (response.ok) {
            window.location.reload();

        } else {
            alert("Erro ao atualizar funcionário.");
        }
    })
}

function deleteEmployee(button) {
    var employeeName = button.getAttribute('data-email');

    const apiUrl = `/api/employee`;
    console.log(employeeName);


    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json', // Defina o tipo de conteúdo para formulário
        },
        body: employeeName
    }).then(response => {
        if (response.ok) {
            window.location.reload();

        } else {
            alert("Erro ao criar funcionário.");
        }
    })
}

function fillForm(button) {
    var employeeName = button.getAttribute('data-name');
    var employeerole = button.getAttribute('data-role');

    var email = button.getAttribute('data-email');

    document.getElementById('Name').value = employeeName;
    document.getElementById('Email').value = email;

    // Verifica qual opção de role corresponde e marca o rádio correspondente
    document.querySelector('input[name=Radios][value=' + employeerole + ']').checked = true;
}

function deleteParkCar(button) {

    var parkCarName = button.getAttribute('data-parkcarsname');

    const apiUrl = `/api/parkCars`;

    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json', // Defina o tipo de conteúdo para formulário
        },
        body: parkCarName
    }).then(response => {
        if (response.ok) {
            window.location.reload();

        } else {
            alert("Erro ao apagar estacioanamento.");
        }
    })
}

function deleteAttraction(button) {

    var attractionName = button.getAttribute('data-attractionname');

    const apiUrl = `/api/attraction`;

    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json', // Defina o tipo de conteúdo para formulário
        },
        body: attractionName
    }).then(response => {
        if (response.ok) {
            window.location.reload();

        } else {
            alert("Erro ao apagar atração.");
        }
    })
}

function deletePark(button) {

    var parkName = button.getAttribute('data-parkname');

    const apiUrl = `/api/park`;

    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json', // Defina o tipo de conteúdo para formulário
        },
        body: parkName
    }).then(response => {
        if (response.ok) {
            window.location.reload();

        } else {
            alert("Erro ao apagar atração.");
        }
    })
}

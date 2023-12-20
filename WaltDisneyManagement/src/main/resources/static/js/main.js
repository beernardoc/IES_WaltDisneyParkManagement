/**
* Template Name: NiceAdmin
* Updated: Sep 18 2023 with Bootstrap v5.3.2
* Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/



var stompClient = null;
var socket = new SockJS('/websocket-endpoint');
stompClient = Stomp.over(socket);
var parkName = getParkNameFromURL();
var chartInstance = {};
var mensagemJson = {"Magic Kingdom": 0, "Epcot": 0, "Hollywood Studios": 0, "Animal Kingdom": 0, "Disney Springs": 0, "Blizzard Beach": 0, "Typhoon Lagoon": 0, "total": 0}


// Visitors
stompClient.connect({}, function (frame) {
  stompClient.subscribe(`/topic/Visitors`, function (mensagem) {

    mensagemJson = JSON.parse(mensagem.body);
    console.log(mensagemJson);

    var visitorsElement = document.getElementById("visitors");
    if (visitorsElement) {
      if (parkName) {
        visitorsElement.innerHTML = mensagemJson[parkName];
        renderVisitorsLineChart(parkName);
      } else { // index
        visitorsElement.innerHTML = mensagemJson["total"];
        renderVisitorsPieChart();
        renderVisitorsLineChart("total");
      }
    }
  });
});


// Pie Chart
function renderVisitorsPieChart() {

  // Destruir o gráfico existente, se houver
  if (chartInstance.pieChart) {
    chartInstance.pieChart.destroy();
  }

  let parques = Object.keys(mensagemJson);
  let labelsValues = parques.slice(0, parques.length - 1);
  let seriesValues = labelsValues.map(parque => mensagemJson[parque]);

  // Criar um novo gráfico com os novos dados
  chartInstance.pieChart = new ApexCharts(document.querySelector("#pieChart"), {
    series: seriesValues,
    chart: {
      height: 350,
      type: 'pie',
      toolbar: {
        show: true
      }
    },
    labels: labelsValues
  });

  chartInstance.pieChart.render();
}


// Area Chart
var dadosGrafico = [0];
var tempo = [""];

function renderVisitorsLineChart(parkVisitors) {
  if (mensagemJson) {

    if (chartInstance.areaChart) {
      chartInstance.areaChart.destroy();
    }

    var now = getCurrentTime();

    if (tempo.length > 0 && now === tempo[tempo.length - 1]) {
      dadosGrafico[dadosGrafico.length - 1] = mensagemJson[parkVisitors];
    } else {
      dadosGrafico.push(mensagemJson[parkVisitors]);
      tempo.push(now);
    }

    if (dadosGrafico.length > 11) {
      dadosGrafico.shift();
      tempo.shift();
    }


    chartInstance.areaChart = new ApexCharts(document.querySelector("#visitorsChart"), {
      series: [{
        name: "Visitors",
        data: dadosGrafico
      }],
      chart: {
        height: 350,
        type: 'area',
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'straight'
      },
      grid: {
        row: {
          colors: ['#f3f3f3', 'transparent'],
          opacity: 0.5
        },
      },
      xaxis: {
        categories: tempo,
      },
      colors: ['#00008b']
    });

    chartInstance.areaChart.render();

  }
}


// Current Time
function getCurrentTime() {
  const now = new Date();
  const hours = now.getHours().toString().padStart(2, '0');
  const minutes = now.getMinutes().toString().padStart(2, '0');
  const seconds = now.getSeconds().toString().padStart(2, '0');
  return `${hours}:${minutes}:${seconds}`;
}


// Park Name
function getParkNameFromURL() {
  var url = window.location.href;
  var parts = url.split('/');
  var parkIndex = parts.indexOf('park');
  var parkName = null;

  if (parkIndex !== -1 && parkIndex < parts.length - 1) {
    parkName = parts[parkIndex + 1].replace(/%20/g, ' ');
  }

  var parkLink = document.getElementById('parkLink');

  if (parkLink) {
    parkLink.textContent = parkName;
    parkLink.href = '/park/' + parkName;
  }

  return parkName;
}


// Gráficos Vazios
$(document).ready(function () {
  if (parkName) renderVisitorsLineChart(parkName);
  else renderVisitorsLineChart("total");
});


// Links
document.addEventListener('DOMContentLoaded', function () {
  // Encontrar todos os elementos com a classe customers-card
  const cardElements = document.querySelectorAll('.customers-card');
  console.log(cardElements);

  cardElements.forEach(function (cardElement) {
    cardElement.addEventListener('click', function () {
      // Verificar se existe o atributo data-parkname no card
        const parkName = cardElement.dataset.parkname;

      // Verificar se existe o atributo data-attraction no card
      const attractionName = cardElement.dataset.attraction;

      const parkinglotName = cardElement.dataset.parkinglotname;
      console.log(parkinglotName);


      // Executar a lógica correspondente com base na verificação
      if (parkName) {
        // Lógica para cards com data-parkname
        console.log('Clicou no card para o parque:', parkName);
        fetch(`/api/park/${parkName}`)
            .then(response => {
              if (response.ok) {
                // Sucesso - você pode redirecionar ou realizar outras ações conforme necessário
                window.location.href = `/park/${parkName}`;
              } else {
                alert("Erro ao obter dados do parque.");
              }
            })
            .catch(error => {
              console.error(error);
              alert("Erro ao processar a solicitação.");
            });
      } else if (attractionName) {
        // Lógica para cards com data-attraction
        console.log('Clicou no card para atração:', attractionName);
        fetch(`/api/attraction/${attractionName}`)
            .then(response => {
              if (response.ok) {
                const currentPath = window.location.pathname;  // Obtém o caminho atual
                const attractionPath = `/attraction/${attractionName}`;

                window.location.href = currentPath + attractionPath;

              } else {
                alert("Erro ao obter dados da atração.");
              }
            })
            .catch(error => {
              console.error(error);
              alert("Erro ao processar a solicitação.");
            });
      } else if (parkinglotName) {
        console.log('Clicou no card para estacionamento:', parkinglotName);
        fetch(`/api/parkCars/${parkinglotName}`)
            .then(response => {
              console.log(response);
              if (response.ok) {
                const parkinglotPath = `/ParkCars/${parkinglotName}`;

                window.location.href = parkinglotPath;

              } else {
                alert("Erro ao obter dados do estacionamento.");
              }
            })
            .catch(error => {
              console.error(error);
              alert("Erro ao processar a solicitação.");
            });
      }
    });
  });
});


// Admin
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


// Template
(function() {
  "use strict";

  /**
   * Easy selector helper function
   */
  const select = (el, all = false) => {
    el = el.trim()
    if (all) {
      return [...document.querySelectorAll(el)]
    } else {
      return document.querySelector(el)
    }
  }

  /**
   * Easy event listener function
   */
  const on = (type, el, listener, all = false) => {
    if (all) {
      select(el, all).forEach(e => e.addEventListener(type, listener))
    } else {
      select(el, all).addEventListener(type, listener)
    }
  }

  /**
   * Easy on scroll event listener
   */
  const onscroll = (el, listener) => {
    el.addEventListener('scroll', listener)
  }

  /**
   * Sidebar toggle
   */
  if (select('.toggle-sidebar-btn')) {
    on('click', '.toggle-sidebar-btn', function(e) {
      select('body').classList.toggle('toggle-sidebar')
    })
  }

  /**
   * Search bar toggle
   */
  if (select('.search-bar-toggle')) {
    on('click', '.search-bar-toggle', function(e) {
      select('.search-bar').classList.toggle('search-bar-show')
    })
  }

  /**
   * Navbar links active state on scroll
   */
  let navbarlinks = select('#navbar .scrollto', true)
  const navbarlinksActive = () => {
    let position = window.scrollY + 200
    navbarlinks.forEach(navbarlink => {
      if (!navbarlink.hash) return
      let section = select(navbarlink.hash)
      if (!section) return
      if (position >= section.offsetTop && position <= (section.offsetTop + section.offsetHeight)) {
        navbarlink.classList.add('active')
      } else {
        navbarlink.classList.remove('active')
      }
    })
  }
  window.addEventListener('load', navbarlinksActive)
  onscroll(document, navbarlinksActive)

  /**
   * Toggle .header-scrolled class to #header when page is scrolled
   */
  let selectHeader = select('#header')
  if (selectHeader) {
    const headerScrolled = () => {
      if (window.scrollY > 100) {
        selectHeader.classList.add('header-scrolled')
      } else {
        selectHeader.classList.remove('header-scrolled')
      }
    }
    window.addEventListener('load', headerScrolled)
    onscroll(document, headerScrolled)
  }

  /**
   * Back to top button
   */
  let backtotop = select('.back-to-top')
  if (backtotop) {
    const toggleBacktotop = () => {
      if (window.scrollY > 100) {
        backtotop.classList.add('active')
      } else {
        backtotop.classList.remove('active')
      }
    }
    window.addEventListener('load', toggleBacktotop)
    onscroll(document, toggleBacktotop)
  }

  /**
   * Initiate tooltips
   */
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
  })

  /**
   * Initiate quill editors
   */
  if (select('.quill-editor-default')) {
    new Quill('.quill-editor-default', {
      theme: 'snow'
    });
  }

  if (select('.quill-editor-bubble')) {
    new Quill('.quill-editor-bubble', {
      theme: 'bubble'
    });
  }

  if (select('.quill-editor-full')) {
    new Quill(".quill-editor-full", {
      modules: {
        toolbar: [
          [{
            font: []
          }, {
            size: []
          }],
          ["bold", "italic", "underline", "strike"],
          [{
              color: []
            },
            {
              background: []
            }
          ],
          [{
              script: "super"
            },
            {
              script: "sub"
            }
          ],
          [{
              list: "ordered"
            },
            {
              list: "bullet"
            },
            {
              indent: "-1"
            },
            {
              indent: "+1"
            }
          ],
          ["direction", {
            align: []
          }],
          ["link", "image", "video"],
          ["clean"]
        ]
      },
      theme: "snow"
    });
  }

  /**
   * Initiate TinyMCE Editor
   */
  const useDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
  const isSmallScreen = window.matchMedia('(max-width: 1023.5px)').matches;

  tinymce.init({
    selector: 'textarea.tinymce-editor',
    plugins: 'preview importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount help charmap quickbars emoticons',
    editimage_cors_hosts: ['picsum.photos'],
    menubar: 'file edit view insert format tools table help',
    toolbar: 'undo redo | bold italic underline strikethrough | fontfamily fontsize blocks | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
    toolbar_sticky: true,
    toolbar_sticky_offset: isSmallScreen ? 102 : 108,
    autosave_ask_before_unload: true,
    autosave_interval: '30s',
    autosave_prefix: '{path}{query}-{id}-',
    autosave_restore_when_empty: false,
    autosave_retention: '2m',
    image_advtab: true,
    link_list: [{
        title: 'My page 1',
        value: 'https://www.tiny.cloud'
      },
      {
        title: 'My page 2',
        value: 'http://www.moxiecode.com'
      }
    ],
    image_list: [{
        title: 'My page 1',
        value: 'https://www.tiny.cloud'
      },
      {
        title: 'My page 2',
        value: 'http://www.moxiecode.com'
      }
    ],
    image_class_list: [{
        title: 'None',
        value: ''
      },
      {
        title: 'Some class',
        value: 'class-name'
      }
    ],
    importcss_append: true,
    file_picker_callback: (callback, value, meta) => {
      /* Provide file and text for the link dialog */
      if (meta.filetype === 'file') {
        callback('https://www.google.com/logos/google.jpg', {
          text: 'My text'
        });
      }

      /* Provide image and alt text for the image dialog */
      if (meta.filetype === 'image') {
        callback('https://www.google.com/logos/google.jpg', {
          alt: 'My alt text'
        });
      }

      /* Provide alternative source and posted for the media dialog */
      if (meta.filetype === 'media') {
        callback('movie.mp4', {
          source2: 'alt.ogg',
          poster: 'https://www.google.com/logos/google.jpg'
        });
      }
    },
    templates: [{
        title: 'New Table',
        description: 'creates a new table',
        content: '<div class="mceTmpl"><table width="98%%"  border="0" cellspacing="0" cellpadding="0"><tr><th scope="col"> </th><th scope="col"> </th></tr><tr><td> </td><td> </td></tr></table></div>'
      },
      {
        title: 'Starting my story',
        description: 'A cure for writers block',
        content: 'Once upon a time...'
      },
      {
        title: 'New list with dates',
        description: 'New List with dates',
        content: '<div class="mceTmpl"><span class="cdate">cdate</span><br><span class="mdate">mdate</span><h2>My List</h2><ul><li></li><li></li></ul></div>'
      }
    ],
    template_cdate_format: '[Date Created (CDATE): %m/%d/%Y : %H:%M:%S]',
    template_mdate_format: '[Date Modified (MDATE): %m/%d/%Y : %H:%M:%S]',
    height: 600,
    image_caption: true,
    quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
    noneditable_class: 'mceNonEditable',
    toolbar_mode: 'sliding',
    contextmenu: 'link image table',
    skin: useDarkMode ? 'oxide-dark' : 'oxide',
    content_css: useDarkMode ? 'dark' : 'default',
    content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:16px }'
  });

  /**
   * Initiate Bootstrap validation check
   */
  var needsValidation = document.querySelectorAll('.needs-validation')

  Array.prototype.slice.call(needsValidation)
    .forEach(function(form) {
      form.addEventListener('submit', function(event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })

  /**
   * Initiate Datatables
   */
  const datatables = select('.datatable', true)
  datatables.forEach(datatable => {
    new simpleDatatables.DataTable(datatable);
  })

  /**
   * Autoresize echart charts
   */
  const mainContainer = select('#main');
  if (mainContainer) {
    setTimeout(() => {
      new ResizeObserver(function() {
        select('.echart', true).forEach(getEchart => {
          echarts.getInstanceByDom(getEchart).resize();
        })
      }).observe(mainContainer);
    }, 200);
  }

})();


// Close Modal
function closeUrgentModal() {
  var modal = $('#urgentModal');
  modal.modal('hide');
  window.location.reload();
}


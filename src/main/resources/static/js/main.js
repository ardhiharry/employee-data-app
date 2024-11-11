$(document).ready(function () {
  // Inisialisasi Flatpickr
  $('#tanggalLahir').flatpickr({
    altInput: true,
    altFormat: "j F Y",
    dateFormat: "Y-m-d",
    static: true,
  });

  // Menampilkan pesan alert
  if (localStorage.getItem('alertMessage')) {
    const alertData = JSON.parse(localStorage.getItem('alertMessage'));
    showAlert(alertData.type, alertData.message);
    localStorage.removeItem('alertMessage');  // Menghapus pesan setelah ditampilkan
  }

  // Button Add
  $('#btnAdd').on('click', function () {
    $('.modal-title').text('Tambah Data Baru');
    $('#btnSave').text('Add');
    $('#form').addClass('form-add').removeClass('form-edit');

    $('#nik').prop('disabled', false).val('');  // Reset NIK dan aktifkan kembali
    $('#namaLengkap').val('');
    $('input[name="jenisKelamin"]').prop('checked', false);
    $('#tanggalLahir').flatpickr({
      altInput: true,
      altFormat: "j F Y",
      dateFormat: "Y-m-d",
      static: true,
      defaultDate: 'today',
    }).clear();
    $('#alamat').val('');
    $('#negara').val('');
  });

  // Button Search
  $('#btnSearch').on('click', function () {
    const nik = $('#nikSearch').val().trim();
    const namaLengkap = $('#namaLengkapSearch').val().trim();

    let url = 'http://localhost:8181/api/v1/employees';

    if (nik || namaLengkap) {
      url += '?';
      if (nik) url += `nik=${nik}`;
      if (nik && namaLengkap) url += '&';
      if (namaLengkap) url += `namaLengkap=${namaLengkap}`;
    }

    $.ajax({
      url: url,
      type: 'GET',
      success: function (result) {
        renderTable(result.data);
      },
      error: function (error) {
        console.log(error);
        setAlertMessage('error', 'Gagal mengambil data.');
      }
    });
  });

  // Button Detail
  $('tbody').on('click', '#btnDetail', function () {
    const nik = $(this).data('nik');
    $.ajax({
      url: 'http://localhost:8181/api/v1/employees/' + nik,
      type: 'GET',
      success: function (result) {
        $('#nikDetail').text(result.data.nik);
        $('#namaLengkapDetail').text(result.data.namaLengkap);
        $('#jenisKelaminDetail').text(result.data.jenisKelamin);
        $('#tanggalLahirDetail').text(formatDateDetail(result.data.tanggalLahir));
        $('#alamatDetail').text(result.data.alamat);
        $('#negaraDetail').text(result.data.negara);
      },
    })
  });

  // Button Edit
  $('tbody').on('click', '#btnEdit', function () {
    $('.modal-title').text('Ubah Data');
    $('#btnSave').text('Save');
    $('#form').addClass('form-edit').removeClass('form-add');
    $('#nik').prop('disabled', true);

    // Get Employee By NIK
    const nik = $(this).data('nik');
    $.ajax({
      url: 'http://localhost:8181/api/v1/employees/' + nik,
      type: 'GET',
      success: function (result) {
        $('#nik').val(result.data.nik);
        $('#namaLengkap').val(result.data.namaLengkap);
        $('input[name="jenisKelamin"][value="' + result.data.jenisKelamin + '"]').prop('checked', true);
        $('#tanggalLahir').flatpickr({
          altInput: true,
          altFormat: "j F Y",
          dateFormat: "Y-m-d",
          static: true,
        }).setDate(result.data.tanggalLahir, true);
        $('#alamat').val(result.data.alamat);
        $('#negara').val(result.data.negara);
      },
    });
  });

  // Button Delete
  $('tbody').on('click', '#btnDelete', function () {
    $('#nik').val($(this).data('nik'));
  });

  // Get All Employee
  $.ajax({
    url: 'http://localhost:8181/api/v1/employees',
    type: 'GET',
    success: function (result) {
      renderTable(result.data);
    },
    error: function (error) {
      console.log(error);
    }
  });

  // Create Employee
  $(document).on('submit', '.form-add', function (event) {
    event.preventDefault();

    const data = {
      nik: $('#nik').val(),
      namaLengkap: $('#namaLengkap').val(),
      jenisKelamin: $('input[name="jenisKelamin"]:checked').val(),
      tanggalLahir: $('#tanggalLahir').val(),
      alamat: $('#alamat').val(),
      negara: $('#negara').val()
    };

    $.ajax({
      url: 'http://localhost:8181/api/v1/employees',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(data),
      success: function (result) {
        $('#formModal').modal('hide');
        setAlertMessage('success', 'Berhasil Menambahkan Data Karyawan!');
        location.reload();
      },
      error: function (error) {
        $('#formModal').modal('hide');
        setAlertMessage('error', error ? error.responseJSON.message : 'Gagal Menambahkan Data Karyawan!');
        location.reload();
      }
    });
  });

  // Update Employee
  $(document).on('submit', '.form-edit', function (event) {
    event.preventDefault();

    const data = {
      namaLengkap: $('#namaLengkap').val(),
      jenisKelamin: $('input[name="jenisKelamin"]:checked').val(),
      tanggalLahir: $('#tanggalLahir').val(),
      alamat: $('#alamat').val(),
      negara: $('#negara').val()
    };

    const nik = $('#nik').val();

    $.ajax({
      url: 'http://localhost:8181/api/v1/employees/' + nik,
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify(data),
      success: function (result) {
        $('#formModal').modal('hide');
        setAlertMessage('success', 'Berhasil Mengubah Data Karyawan!');
        location.reload();
      },
      error: function (error) {
        $('#formModal').modal('hide');
        setAlertMessage('error', error ? error.responseJSON.message : 'Gagal Mengubah Data Karyawan!');
        location.reload();
      }
    });
  });

  // Delete Employee
  $('#modalBtnDelete').on('click', function () {
    const nik = $('#nik').val();

    $.ajax({
      url: 'http://localhost:8181/api/v1/employees/' + nik,
      type: 'DELETE',
      success: function (result) {
        $('#deleteModal').modal('hide');
        setAlertMessage('success', 'Berhasil Menghapus Data Karyawan!');
        location.reload();
      },
      error: function (error) {
        $('#deleteModal').modal('hide');
        setAlertMessage('error', error ? error.responseJSON.message : 'Gagal Menghapus Data Karyawan!');
        location.reload();
      }
    });
  });

  // Menyimpan pesan alert ke localStorage
  function setAlertMessage(type, message) {
    const alertData = { type: type, message: message };
    localStorage.setItem('alertMessage', JSON.stringify(alertData));
  }

  // Show alert
  function showAlert(type, message) {
    const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
    const messageDuration = type === 'success' ? 3000 : 5000;

    $("." + alertClass).text(message).removeClass("visually-hidden").addClass("d-block").fadeIn();

    setTimeout(function () {
      $("." + alertClass).fadeOut(function () {
        $(this).removeClass("d-block").addClass("visually-hidden");
      });
    }, messageDuration);
  }
});

function renderTable(data) {
  const tbody = $('tbody');
  tbody.empty();

  data.forEach((item, index) => {
    const row = $('<tr>');
    row.append($('<td>').text(index + 1));
    row.append($('<td>').text(item.nik));
    row.append($('<td>').text(item.namaLengkap));
    row.append($('<td>').text(age(item.tanggalLahir)));
    row.append($('<td>').text(formatDate(item.tanggalLahir)));
    row.append($('<td>').text(item.jenisKelamin));
    row.append($('<td>').text(item.alamat));
    row.append($('<td>').text(item.negara));

    const actionBtn = $('<td class="text-center">');
    actionBtn.append(
      $('<button type="button" class="btn btn-info btn-sm me-2" id="btnDetail" data-bs-toggle="modal" data-bs-target="#detailModal">')
        .text('Detail').data('nik', item.nik)
    );
    actionBtn.append(
      $('<button type="button" class="btn btn-warning btn-sm me-2" id="btnEdit" data-bs-toggle="modal" data-bs-target="#formModal">')
        .text('Edit').data('nik', item.nik)
    );
    actionBtn.append(
      $('<button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" id="btnDelete" data-bs-target="#deleteModal">')
        .text('Delete').data('nik', item.nik)
    );

    row.append(actionBtn);
    tbody.append(row);
  });
}

function age(date) {
  const birthDate = new Date(date);
  const today = new Date();

  let age = today.getFullYear() - birthDate.getFullYear();
  const month = today.getMonth() - birthDate.getMonth();

  if (month < 0 || (month === 0 && today.getDate() < birthDate.getDate())) {
    age--;
  }

  return age;
}

function formatDate(date) {
  const birthDate = new Date(date);
  const day = String(birthDate.getDate()).padStart(2, '0');
  const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  const month = monthNames[birthDate.getMonth()];
  const year = birthDate.getFullYear();

  return `${day}-${month}-${year}`;
}

function formatDateDetail(date) {
  const month = [
    "Januari", "Februari", "Maret", "April", "Mei", "Juni",
    "Juli", "Agustus", "September", "Oktober", "November", "Desember"
  ];
  const birthDate = new Date(date);
  const day = birthDate.getDate();
  const getMonth = month[birthDate.getMonth()];
  const year = birthDate.getFullYear();
  return `${day} ${getMonth} ${year}`;
}
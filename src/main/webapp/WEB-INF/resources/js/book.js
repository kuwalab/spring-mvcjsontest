var JST = {};
JST['tr'] = _.template(
  '<tr>' +
  '<th>' +
   '<input type="hidden" name="initBookId" value="<%- bookId %>">' +
   '<input type="text" size="3" placeholder="id" name="bookId" value="<%- bookId %>">' +
  '</th>' +
  '<th><input type="text" size="20" placeholder="書名" name="bookName" value="<%- bookName %>"></th>' +
  '<th><input type="text" size="5" placeholder="価格" name="price" value="<%- price %>"></th>' +
  '<th><input type="button" value="更新" name="update"><input type="button" value="削除" name="delete"></th>' +
  '</tr>'
);

var $tbody = $('tbody');

// 登録済みの一覧を読み直す
var getBookList = function() {
  $.ajax({
    method: 'get',
    url: 'books',
    dataType: 'json'
  }).done(function(data) {
    var i;
    $tbody.empty();
    for (i = 0; i < data.length; i++) {
      $tbody.append(JST['tr'](data[i]));
    }
  }).fail(function(jqXHR, textStatus, errorThrown) {
    console.log(textStatus);
  });
};

var setTrEvent = function() {
  // 行ごとの更新ボタンの処理
  $('table').on('click', 'input[name="update"]', function() {
    var $tr = $(this).parents('tr');
    var sendData = {
      bookId: $tr.find('input[name="bookId"]').val(),
      bookName: $tr.find('input[name="bookName"]').val(),
      price: $tr.find('input[name="price"]').val()
    };
    
    var initBookId = $tr.find('input[name="initBookId"]').val();

    $.ajax({
      method: 'put',
      contentType: 'application/json;charset=utf-8',
      data: JSON.stringify(sendData),
      url: 'books/' + initBookId,
      dataType: 'json'
    }).done(function(data) {
      getBookList();
    }).fail(function(jqXHR, textStatus, errorThrown) {
      console.log(textStatus);
    });
  });

  // 行ごとのdeleteボタンの処理
  $('table').on('click', 'input[name="delete"]', function() {
    var bookId = $(this).parents('tr').find('input[name="initBookId"]').val();
    $.ajax({
      method: 'delete',
      url: 'books/' + bookId,
      dataType: 'json'
    }).done(function(data) {
      getBookList();
    }).fail(function(jqXHR, textStatus, errorThrown) {
     console.log(textStatus);
    });
  });
};

getBookList();
setTrEvent();

$('#insert').on('click', function() {
  var sendData = {
      bookId: $('#insBookId').val(),
      bookName: $('#insBookName').val(),
      price: $('#insPrice').val()
  };
  
  $.ajax({
    method: 'post',
    contentType: 'application/json;charset=utf-8',
    data: JSON.stringify(sendData),
    url: 'books',
    dataType: 'json'
  }).done(function(data) {
    $('#insBookId').val('');
    $('#insBookName').val('');
    $('#insPrice').val('');
    getBookList();
  }).fail(function(jqXHR, textStatus, errorThrown) {
    console.log(textStatus);
  });
});

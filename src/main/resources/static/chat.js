$(function() {
    'use strict';

    var client;

    var test;
    function showMessage(mesg)
    {
	$('#messages').append('<tr>' +
			      '<td>' + mesg.from + '</td>' +
			      '<td>' + mesg.topic + '</td>' +
			      '<td>' + mesg.message + '</td>' +
			      '<td>' + mesg.time + '</td>' +
			      '</tr>');
    }

    function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	$('#from').prop('disabled', connected);
	$('#text').prop('disabled', !connected);
	if (connected) {
	    $("#conversation").show();
	    $('#text').focus();
	}
	else $("#conversation").hide();
	$("#messages").html("");
    }

    $("form").on('submit', function (e) {
	e.preventDefault();
    });

    $('#from').on('blur change keyup', function(ev) {
	$('#connect').prop('disabled', $(this).val().length == 0 );
    });
    $('#connect,#disconnect,#text').prop('disabled', true);

    $('#connect').click(function() {
	client = Stomp.over(new SockJS('/chat'));



	client.connect({'simpUser1':'tester'}, function (frame) {

	    setConnected(true);
	    client.subscribe('/topic/messages', function (message) {
	    alert(JSON.parse(message.body));
		showMessage(JSON.parse(message.body));
	    });

	    client.subscribe("/user/queue/position-updates" , function (message) {
	    alert("haha");	
	    alert(JSON.parse(message.body));
		//showMessage(JSON.parse(message.body));
	    });
	});
    });

    $('#disconnect').click(function() {
	if (client != null) {
	    client.disconnect();
	    setConnected(false);
	}
	client = null;
    });

    $('#send').click(function() {
	var topic = $('#topic').val();
	client.send("/app/chat/" + topic, {}, JSON.stringify({
	    from: $("#from").val(),
	    text: $('#text').val(),
	}));
	$('#text').val("");
    });
});

var stompClient = null;

function setConnected(connected) {
    if (!connected) {
        $("#connect").show();
        $("#name-for-connection").show();
        $("#name-for-connection-label").show();
        $("#conversation").hide();
        $("#connections-overview").hide();
        $("#disconnect").hide();
        $("#send").hide();
        $("#message").hide();
        $("#message-label").hide();
        $("#connections-amount-title").hide();
    }
    else {
        $("#connect").hide();
        $("#name-for-connection").hide();
        $("#name-for-connection-label").hide();
        $("#conversation").show();
        $("#connections-overview").show();
        $("#disconnect").show();
        $("#send").show();
        $("#message").show();
        $("#message-label").show();
        $("#connections-amount-title").show();
    }
    $("#all-messages").html("");
}

function connect() {
    var socket = new SockJS('/socketalk-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        subsribeToInitialConnectionTopic();
        subsribeToAllMessagesTopic();
        sendInitialConnectMessage();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendInitialConnectMessage() {
    stompClient.send("/app/initial-connection", {}, JSON.stringify({'name': $("#name-for-connection").val()}));
}

function sendMessage() {
    stompClient.send("/app/message", {}, JSON.stringify({'text': $("#message").val()}));
}

function subsribeToInitialConnectionTopic() {
    stompClient.subscribe('/topic/initial-connection-information', function (connectionInformation) {
        showConnections(JSON.parse(connectionInformation.body));
    });
}

function subsribeToAllMessagesTopic() {
    stompClient.subscribe('/topic/all-messages', function (message) {
        showMessages(JSON.parse(message.body).text);
    });
}

function showMessages(message) {
    $("#all-messages").append("<tr><td>" + message + "</td></tr>");
}

function showConnections(connectionInformation) {
    rerenderListOfConnectedUsers(connectionInformation);
    rerenderAmountOfConnectedUsers(connectionInformation.amountOfConnectedUsers);
}

function rerenderAmountOfConnectedUsers(amountOfConnectedUsers) {
    $("#connections-amount").append(amountOfConnectedUsers);
}

function rerenderListOfConnectedUsers(connectionInformation) {
    connectionInformation.namesOfConnectedUsers.forEach(function (nameOfConnectedUser) {
        $("#connections").append("<tr><td>" + nameOfConnectedUser + "</td></tr>");
    });
}

$(function () {
    setConnected(false);
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});
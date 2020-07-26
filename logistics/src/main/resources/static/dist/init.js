// 动态脚本
// function loadScriptString(code) {
//     var script = document.createElement("script");
//     script.type = "text/javascript";
//     try {
//         script.appendChild(document.createTextNode(code));
//     } catch (ex) {
//         script.text = code;
//     }
//     document.body.appendChild(script);
// }

function message(info) {
    $('#message p').text(info);
    $('#message').removeAttr('hidden');
    setTimeout(() => { $('#message').attr('hidden', true) }, 3000);
}

var page = {
    login: false,
    fixed: {
        from: 0,
        to: 5
    },
    submitted: {
        from: 0,
        to: 5
    }
}

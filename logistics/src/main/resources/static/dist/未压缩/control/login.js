function login() {
    let id = $('#StudentIDCode').val();
    let password = $('#Password').val();
    let check = $('input:checked')[0];
    if (check && id.length > 0 && password.length > 0) {
        let type = $('input:checked')[0].id;
        let ID = type === 'student' ? 'studentID' : 'code';
        // fetch(`http://localhost:8080/${type}/login`, {
        fetch(`/${type}/login`, {
            method: 'POST',
            headers: {'content-type': 'application/x-www-form-urlencoded'},
            body: `${ID}=${id}&password=${password}`,
        }).then(response => response.json())
            .then(json => {
                if (json.code != 200) {
                    message(json.message);
                } else {
                    page.login = true;
                    json.obj.id = type;
                    info(json.obj);
                }
            })
            .catch(error => {
                console.log(error);
                message('服务器错误');
            });
    } else message('填写表单后再提交');
}
function register() {
    $('#register').modal('hide');
    let type = $('input:checked')[0].id;
    let id = $('#id').val();
    let p1 = $('#RPassword').val();
    let p2 = $('#RPassword2').val();
    if (p1 === p2) {
        let data = type === 'student' ? {
            stuID: id,
            name: $('#name').val(),
            password: p1,
            tel: $('#tel').val(),
            email: $('#email').val(),
            romm: $('#room').val(),
        } : {
                code: id,
                name: $('#name').val(),
                password: p1,
                tel: $('#tel').val(),
            };
        // fetch(`http://localhost:8080/${type}/register?password=${p2}`, {
            fetch(`/${type}/register?password=${p2}`, {
            method: 'POST',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(data),
        }).then(response => response.json())
            .then(json => {
                if (json.code != 200) {
                    // console.log(json);
                    message(json.message);
                } else {
                    json.obj.id = type;
                    info(json.obj);
                }
            })
            .catch(error => {
                console.log(error);
                message('服务器错误');
            });
    } else message('密码不相同');
}

function registerShow() {
    let check = $('input:checked')[0];
    if (check) {
        let type = $('input:checked')[0].id;
        $('#registerForm').html(registerView(type));
        $('#register').modal('show');
    } else message('选择后再注册');
}

function registerView(type) {
    let view = {};
    view.student = `
<div class="form-group row">
    <label for="id" class="col-sm-2 col-form-label">学号</label>
    <div class="col-sm-10">
        <input type="number" class="form-control" id="id"
            aria-describedby="StudentIDHelpBlock" required min="100000">
    </div>
    <small id="StudentIDHelpBlock" class="form-text text-muted col">
        只接受数字且长度大于5
    </small>
</div>
<div class="form-group row">
    <label for="RPassword" class="col-sm-2 col-form-label">密码</label>
    <div class="col-sm-10">
        <input type="password" class="form-control" id="RPassword" required>
    </div>
</div>
<div class="form-group row">
    <label for="RPassword2" class="col-sm-3 col-form-label">确认密码</label>
    <div class="col-sm-9">
        <input type="password" class="form-control" id="RPassword2" required>
    </div>
</div>
<div class="form-group row">
    <label for="email" class="col-sm-2 col-form-label">邮箱</label>
    <div class="col-sm-10">
        <input id="email" type="email" class="form-control" required />
    </div>
</div>
<div class="form-row">
    <div class="col-5">
        <input type="text" class="form-control" placeholder="姓名" id="name" required>
    </div>
    <div class="col">
        <input type="tel" class="form-control" placeholder="手机号码" id="tel" required
            pattern="^\\d{11}$">
    </div>
    <div class="col">
        <input type="text" class="form-control" placeholder="寝室编码" id="room" required>
    </div>
</div>`;
    view.worker = `
<div class="form-group row">
<label for="id" class="col-sm-2 col-form-label">工号</label>
<div class="col-sm-10">
    <input type="text" class="form-control" id="id" required>
</div>
</div>
<div class="form-group row">
<label for="RPassword" class="col-sm-2 col-form-label">密码</label>
<div class="col-sm-10">
    <input type="password" class="form-control" id="RPassword" required>
</div>
</div>
<div class="form-group row">
<label for="RPassword2" class="col-sm-3 col-form-label">确认密码</label>
<div class="col-sm-9">
    <input type="password" class="form-control" id="RPassword2" required>
</div>
</div>
<div class="form-row">
<div class="col-5">
    <input type="text" class="form-control" placeholder="姓名" id="name" required>
</div>
<div class="col">
    <input type="tel" class="form-control" placeholder="手机号码" id="tel" required
        pattern="^\\d{11}$">
</div>
</div>`;
    return view[type];
}
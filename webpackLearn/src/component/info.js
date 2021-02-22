function info(obj) {
    let view = infoView(obj);
    $('#login').hide();
    // $('#pic').attr('src', "http://localhost:8080" + obj.photo);
    $('#pic').attr('src', obj.photo);
    $('#info').html(view);
    let funcs = commonFunc(obj.id);
    $('#upload').html(funcs[0]);
    $('#update').html(funcs[1]);
}

function repair(id) {
    let data = {
        room: $('#room').val(),
        note: $('#note').val(),
    }
    // fetch(`http://localhost:8080/repair/submit?stuID=${id}`, {
    fetch(`/repair/submit?stuID=${id}`, {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(data),
    }).then(response => response.json())
        .then(json => {
            message(json.message);
            init();
        })
        .catch(error => {
            console.log(error);
            message('服务器错误');
        });
}

var upload = {
    // pic: 0,
    // temp: function () {
    //     pic = ('#uploadPic').files[0];
    // },
    action: function () {
        let type = $('#room').length != 0 ? 'student' : 'worker';
        let ID = type === 'student' ? "stuID" : "code";
        let id = document.getElementById('id').innerText.split('：')[1];
        let data = new FormData();
        data.append('file', document.getElementById('uploadPic').files[0]);
        data.append(ID, id);
        // fetch(`http://localhost:8080/${type}/uploadAvatar`, {
        fetch(`/${type}/uploadAvatar`, {
            method: 'POST',
            body: data,
        }).then(response => response.json())
            .then(json => {
                if (json.code != 200) {
                    message(json.message);
                } else {
                    $('#pic').attr('src', '');//reload
                    // $('#pic').attr('src', "http://localhost:8080" + json.message);
                    $('#pic').attr('src', json.message);
                    $('#uploadModal').modal('hide');
                }
            })
            .catch(error => {
                console.log(error);
                message('服务器错误');
            });
    },
}

function update() {
    let type = $('#room').length != 0 ? 'student' : 'worker';
    let ID = type === 'student' ? 'studentID' : 'code';
    let Oid = document.getElementById('id').innerText.split('：')[1];
    let id = $('#Nid').val();
    let p1 = $('#OPassword').val();
    let p2 = $('#NPassword').val();
    let data = type === 'student' ? {
        stuID: id,
        name: $('#name').val(),
        password: p2,
        tel: $('#tel').val(),
        email: $('#email').val(),
        romm: $('#room').val(),
        // photo: $('#pic').attr('src').split('8080')[1],
        photo: $('#pic').attr('src'),
    } : {
        code: id,
        name: $('#name').val(),
        password: p2,
        tel: $('#tel').val(),
        // photo: $('#pic').attr('src').split('8080')[1],
        photo: $('#pic').attr('src'),
    };
    // fetch(`http://localhost:8080/${type}/update?password=${p1}&${ID}=${Oid}`, {
    fetch(`/${type}/update?password=${p1}&${ID}=${Oid}`, {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(data),
    }).then(response => response.json())
        .then(json => {
            if (json.code != 200) {
                message(json.message);
                $('#updateModal').modal('hide');
            } else {
                let obj = json.obj;
                let view = infoView(obj);
                // $('#pic').attr('src', "http://localhost:8080" + obj.photo);
                $('#pic').attr('src', obj.photo);
                $('#info').html(view);
                $('#updateModal').modal('hide');
            }
        })
        .catch(error => {
            console.log(error);
            message('服务器错误');
        });
}

function infoView(obj) {
    let user = obj;
    let student = `
        <div class="card-body">
            <h5 class="card-title">姓名：${user.name}</h5>
            <p class="card-text" id="id">学号：${user.stuID}</p>
            <p class="card-text">电话：${user.tel}</p>
        </div>
        <div class="repair">
            <div class="form-group">
                <label for="room">寝室号</label>
                <input type="text" class="form-control" id="room" value=${user.room} disabled>
            </div>
            <div class="form-group">
                <label for="note">备注</label>
                <textarea class="form-control" id="note" rows="5"></textarea>
            </div>
            <button type="button" class="btn btn-primary" 
                onclick="repair('${user.stuID}')"
                >报修</button>
        </div>`;
    let worker = `
            <div class="card-body">
                <h5 class="card-title">姓名：${user.name}</h5>
                <p class="card-text" id="id">工号：${user.code}</p>
                <p class="card-text">电话：${user.tel}</p>
            </div>`;
    return user.id === "student" ? student : worker;
}

function commonFunc(id) {
    let upload = `
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#uploadModal">
        修改头像
    </button>
    <div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="upload"
        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="upload">上传</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">X</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label class="col col-form-label" for="uploadPic" data-browse="选择图片">选择图片</label>
                    <input type="file" id="uploadPic" accept="image/*">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="upload.action()">上传</button>
                </div>
            </div>
        </div>
    </div>`;
    let body = id === 'student' ?
        `<div class="form-group row">
        <label for="Nid" class="col-sm-2 col-form-label">学号</label>
        <div class="col-sm-10">
            <input type="number" class="form-control" id="Nid"
                aria-describedby="StudentIDHelpBlock" required min="100000">
        </div>
        <small id="StudentIDHelpBlock" class="form-text text-muted col">
            只接受数字且长度大于5
        </small>
    </div>
    <div class="form-group row">
        <label for="OPassword" class="col-sm-2 col-form-label">旧密码</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="OPassword" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="NPassword" class="col-sm-3 col-form-label">新密码</label>
        <div class="col-sm-9">
            <input type="password" class="form-control" id="NPassword" required>
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
    </div>`
        : `<div class="form-group row">
        <label for="Nid" class="col-sm-2 col-form-label">工号</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="Nid" required>
        </div>
        </div>
        <div class="form-group row">
        <label for="OPassword" class="col-sm-2 col-form-label">旧密码</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="OPassword" required>
        </div>
        </div>
        <div class="form-group row">
        <label for="NPassword" class="col-sm-3 col-form-label">新密码</label>
        <div class="col-sm-9">
            <input type="password" class="form-control" id="NPassword" required>
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
    let update = `
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#updateModal">
        更改个人信息
    </button>
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="update"
        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="update">更改</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">X</span>
                    </button>
                </div>
                <div class="modal-body">
                ${body}
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary"
                        onclick="update()">更改</button>
                </div>
            </div>
        </div>
    </div>`;
    return [upload, update];
}

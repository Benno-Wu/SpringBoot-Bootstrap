function init() {
    // console.log('hi');
    // fetch(`http://localhost:8080/repair/top5`, {
    fetch(`/repair/top5`, {
        method: 'POST',
    }).then(response => response.json())
        .then(json => {
                if (json.code != 200) {
                    message(json.message);
                } else {
                    // console.log(json.obj);
                    detail(json.obj);
                    page.fixed = {from: 0, to: 5};
                    page.submitted = {from: 0, to: 5};
                }
            }
        )
        .catch(error => {
            console.log(error);
            message('服务器错误');
        });
};
init();

function fix(event) {
    let data = {};
    data.room = event.target.previousElementSibling.previousElementSibling.innerText;
    data.note = event.target.previousElementSibling.innerText;
    if (page.login) {
        let type = $('#room').length != 0 ? 'student' : 'worker';
        if (type === 'worker') {
            let id = document.getElementById('id').innerText.split('：')[1];
            // fetch(`http://localhost:8080/repair/fix?code=${id}`, {
            fetch(`/repair/fix?code=${id}`, {
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
        } else message('请维修工登录');
    } else message('请登录');
}

function pageUp(event) {
    let type=event.target.dataset.type;
    let from_ = 2 * page[type].from - page[type].to;
    let to_ = page[type].from;
    if (from_ < 0) {
        message('到顶了！');
    } else
        // fetch(`http://localhost:8080/repair/${type}?from=${from_}&to=${to_}`, {
        fetch(`/repair/${type}?from=${from_}&to=${to_}`, {
            method: 'POST',
        }).then(response => response.json())
            .then(json => {
                if (json.code != 200) {
                    message(json.message);
                } else {
                    let obj = {};
                    obj[type] = json.obj;
                    if (json.obj.length > 0) {
                        page[type] = {from: from_, to: to_};
                        detail(obj);
                    } else message('到顶了！');
                }
            })
            .catch(error => {
                console.log(error);
                message('服务器错误');
            });
}

function pageDown(event) {
    let type=event.target.dataset.type;
    let from_ = page[type].to;
    let to_ = 2 * page[type].to - page[type].from;
    // fetch(`http://localhost:8080/repair/${type}?from=${from_}&to=${to_}`, {
    fetch(`/repair/${type}?from=${from_}&to=${to_}`, {
        method: 'POST',
    }).then(response => response.json())
        .then(json => {
            if (json.code != 200) {
                message(json.message);
            } else {
                let obj = {};
                obj[type] = json.obj;
                if (json.obj.length > 0) {
                    page[type] = {from: from_, to: to_};
                    detail(obj);
                } else message('到底了！');
            }
        })
        .catch(error => {
            console.log(error);
            message('服务器错误');
        });
}

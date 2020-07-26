function detail(obj) {
    if ('fixed' in obj) {
        let fixed = new String();
        obj.fixed.forEach(cardInfo => {
            fixed += card({ 'room': cardInfo.room, 'note': cardInfo.note, 'ability': '', 'bool': true });
        });
        fixed += paged('fixed');
        $('#fixed').html(fixed);
    }
    if ('submitted' in obj) {
        let submitted = new String();
        obj.submitted.forEach(cardInfo => {
            submitted += card({ 'room': cardInfo.room, 'note': cardInfo.note, 'ability': 'fix(event)', 'bool': false });
        });
        submitted += paged('submitted');
        $('#submitted').html(submitted);
    }
}

function card(state) {
    return `<div class="card m-3 w-25 mh-50">
    <div class="card-self m-3 h-100">
        <h5 class="card-title">${state.room}</h5>
        <p class="card-text ">${state.note}</p>
        <button type="button" class="btn btn-primary mw-8r" onclick="${state.ability}" ${state.bool ? 'disabled' : ''}>已维修</button>
    </div>
</div>`;
}

function paged(type) {
    return `<div class="bottom-end m-3">
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-end">
            <li class="page-item"> <a class="page-link" onclick="pageUp(event)" data-type="${type}">Previous</a> </li>
            <li class="page-item"> <a class="page-link" onclick="pageDown(event)" data-type="${type}">Next</a> </li>
        </ul>
    </nav>
</div>`;
}

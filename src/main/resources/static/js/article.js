import {fetchCategories} from "./fetch-functions.js";

$(document).ready(function () {
    const select = $('#cat');
    fetchCategories(select);
});
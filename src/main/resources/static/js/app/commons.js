/**
 * Created by qiushengming on 2018/5/25.
 */
Array.prototype.contains = function (elem) {
  for (var i = 0; i < this.length; i++) {
    if (this[i] === elem) {
      return true;
    }
  }
  return false;
};

String.prototype.trim = function () {
  return this.replace(/(^\s*)|(\s*$)/g, "");
};

String.prototype.endWith = function (s) {
  if (s === null || s === ""
    || this.length === 0
    || s.length > this.length) {
    return false;
  }
  return this.substring(this.length - s.length) === s;
};

String.prototype.startWith = function (s) {
  if (s === null || s === ""
    || this.length === 0
    || s.length > this.length) {
    return false;
  }
  return this.substr(0, s.length) === s;
};

function array2Str(values) {
  if (values === null) {
    return "";
  }

  var ids = "";
  for (var i = 0; i < values.length; i++) {
    if (i < (values.length - 1)) {
      ids = ids + values[i] + ",";
    } else {
      ids = ids + values[i];
    }
  }
  return ids;
}

function download_file(url, params) {
  var form = document.createElement("form");
  document.body.appendChild(form);
  form.action = url;
  form.method = "post";
  form.style.display = "none";

  for (var name in params) {
    var input = document.createElement("input");
    input.name = name;
    input.value = params[name];
    form.appendChild(input);
  }
  form.submit();
  document.body.removeChild(form);
}
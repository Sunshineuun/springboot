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

function formatter(dictionaryKey, value) {
  var values = dictionary[dictionaryKey];

  if (!value || !values) {
    return "";
  }

  for (var i = 0; i < values.length; i++) {
    if (values[i][0] === value)
      return values[i][1];
  }

  return value;
}

function repeat(s, count) {
  return new Array(count + 1).join(s);
}

/**
 * 格式化json
 */
function formatJson(json) {

  var i = 0,
    len = 0,
    tab = "    ",
    targetJson = "",
    indentLevel = 0,
    inString = false,
    currentChar = null;


  for (i = 0, len = json.length; i < len; i += 1) {
    currentChar = json.charAt(i);

    switch (currentChar) {
      case '{':
      case '[':
        if (!inString) {
          targetJson += currentChar + "\n" + repeat(tab, indentLevel + 1);
          indentLevel += 1;
        } else {
          targetJson += currentChar;
        }
        break;
      case '}':
      case ']':
        if (!inString) {
          indentLevel -= 1;
          targetJson += "\n" + repeat(tab, indentLevel) + currentChar;
        } else {
          targetJson += currentChar;
        }
        break;
      case ',':
        if (!inString) {
          targetJson += ",\n" + repeat(tab, indentLevel);
        } else {
          targetJson += currentChar;
        }
        break;
      case ':':
        if (!inString) {
          targetJson += ": ";
        } else {
          targetJson += currentChar;
        }
        break;
      case ' ':
      case "\n":
      case "\t":
        if (inString) {
          targetJson += currentChar;
        }
        break;
      case '"':
        if (i > 0 && json.charAt(i - 1) !== '\\') {
          inString = !inString;
        }
        targetJson += currentChar;
        break;
      default:
        targetJson += currentChar;
        break;
    }
  }
  return targetJson;
}

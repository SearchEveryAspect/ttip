
function SubjectField() {
  this.btn = false;
  this.sub;
}

SubjectField.prototype = {
  init: function() {
    var c = this;
    $( "#subjectfield .autocomplete" ).autocomplete({
      source: subjects,
      response: function( event, ui ) {
        document.getElementById("btn").disabled = false;
        c.inputHandler(this);
      },
      close: function() {
        c.inputHandler(this);
      }
    });
  },
  inputHandler: function(c) {
    //TODO:make case-insensitive
    this.inArray($(c).val());
    checkBtn();    
  },
  inArray: function(s) {
    if (subjects.indexOf(s) > -1) {
      this.sub = s.replace(/å|ä/gi, 'a').replace(/ö/gi, 'o').toLowerCase();
      this.btn = true;
    } else {
      this.btn = false;

    }
  },
  getBtn: function() {
    return this.btn;
  }
}

function TimePeriod(max) {
  this.errorDiv = "#searchbox #error p";
  this.fromId = document.getElementById("from");
  this.toId = document.getElementById("to");
  this.btn = false;
  this.max = max;
  this.validDateRegex = /^(19[7-9]\d|200\d|201[0-5])[\-](0[1-9]|1[0-2])[\-](0[1-9]|[12]\d|3[01])$/i;
  this.patt = new RegExp(this.validDateRegex);
  this.from;
  this.to;
}

TimePeriod.prototype = {
  init: function() {
    var c = this;
    $( "#from" ).attr("placeholder", "yyyy-mm-dd").datepicker({
        defaultDate: "-1m",
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1,
        maxDate: c.max,
        onChangeMonthYear: function(y, m, o) {
          if (m < 10) {
            m = "0"+m;
          }
          $(this).val(y+"-"+m+"-01");
          c.from = $(this).val();
        },
        onClose: function( selectedDate ) {
          $( "#to" ).datepicker( "option", "minDate", selectedDate );
          c.to = $( "#to" ).val();
          c.from = $(this).val();
          c.inputHandler();
        }
      });
    $( "#to" ).attr("placeholder", "yyyy-mm-dd").datepicker({
        defaultDate: "-1w",
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1,
        maxDate: c.max,
        onChangeMonthYear: function(y, m, o) {
          if (m < 10) {
            m = "0"+m;
          }
          $(this).val(y+"-"+m+"-01");
          c.to = $(this).val();
        },
        onClose: function( selectedDate ) {
          $( "#from" ).datepicker( "option", "maxDate", selectedDate );
          c.from = $("#from" ).val();
          c.to = $(this).val();
          c.inputHandler();
        }
    });
 
  },
  inputHandler: function() {

    if ((this.notDefined(this.from)) || (this.notDefined(this.to))) {
      this.btn = false;
      this.clearAll();
      return;
    }

    if(this.isValidString(this.from) && 
        this.isValidString(this.to)) 
    {    
      switch(this.getMax(this.from, this.to, this.max)) {
        case this.from:
          $("#from").val(this.max);
          $("#to").val(this.max);
          this.from = $("#from").val();
          this.to = $("#to").val();

          break;
        case this.to:
          $("#to").val(this.max);
          this.to = $("#to").val();

          break;
        case this.max:
          break;
      }
      this.btn = true;
      this.clearAll();
    } else {
        this.fromId.style.borderBottom="2px solid red";
        this.toId.style.borderBottom="2px solid red";
        this.btn = false;
        $(this.errorDiv).text("Inkorrekt tidsformat.");
    }
    checkBtn();
  },
  isValidString: function(str) {
    if (this.patt.test(str)) return true;
    console.error("Not valid string");
    return false;
  },
  getMax: function(_from, _to, _max) {
    var from = Date.parse(_from);
    var to = Date.parse(_to);
    var max = Date.parse(_max);
    switch(Math.max(from,to,max)) {
      case max: 
        return _max;
        break;
      case to:
        return _to;
        break;
      case from:
        return _from;
        break;
      default:
        console.error("Can't getMax of " + _from + ", " + _to + ", " + _max)
        return;
      }

  },
  notDefined: function(v) {
    if (v === undefined || v === "") return true;
    return false;
  },
  clearAll: function() {
    $(this.errorDiv).text("");
    this.fromId.style.borderBottom = "1px solid rgba(60,60,60, 1)";
    this.toId.style.borderBottom = "1px solid rgba(60,60,60, 1)";

  },
  getBtn: function() {
    return this.btn;
  }
}

function searchInit() {
  tp = new TimePeriod("2015-05-20");
  sf = new SubjectField();
  tp.init();
  sf.init();
}

function checkBtn() {
    //console.log("TP: " + tp.getBtn() + " SF: " + sf.getBtn());
    if ((tp.getBtn() == true) && (sf.getBtn() == true)) {
      document.getElementById("btn").disabled = false;
    } else {
      document.getElementById("btn").disabled = true;
    }
}

function search() {
  charts[0].updateSubject(tp.from, tp.to, sf.sub);
}







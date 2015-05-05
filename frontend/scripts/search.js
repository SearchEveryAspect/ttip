/*This script is used on search.html and will wait for user input
/*before calling parseJSON.js*/



function SubjectField() {
  this.btn = false;
  this.sub;
}

SubjectField.prototype = {
  init: function() {
    var c = this;
    $( "#autocomplete" ).autocomplete({
      source: subjects,
      change: function() {
        c.inputHandler(this);
      }
    });


  },
  inputHandler: function(c) {
    //TODO:make case-insenstive
    this.inArray($(c).val());
    checkBtn();    
  },
  inArray: function(s) {
    if (subjects.indexOf(s) > -1) {
      console.log("Is in array: " + s );
      this.sub = s;
      this.btn = true;
    } else {
      console.log("not in array");
      this.btn = false;

    }
  },
  getBtn: function() {
    return this.btn;
  }
}

function TimePeriod() {
  this.errorDiv = "#searchbox #error p";
  this.errorStyle = {"border-bottom": "2px solid red"};
  this.defaultStyle = {"border": "none"};
  this.btn = false;

  //Should be gotten from json object.
  this.maxxi = "2015-04-23" 
  this.maxDate = Date.parse(this.maxxi);

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
        maxDate: "-7d",
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
        maxDate: "-7d",
        onClose: function( selectedDate ) {
          $( "#from" ).datepicker( "option", "maxDate", selectedDate );
          c.from = $("#from" ).val();
          c.to = $(this).val();
          c.inputHandler();
        }
    });
 
  },

    inputHandler: function() {
      console.log("Current from is: " + this.from);
      console.log("Current to is: " + this.to);

      if ((this.notDefined(this.from)) || (this.notDefined(this.to))) {
        this.btn = false;
        this.clearAll();
        return;
      }

      //Start by checking if its a valid string
      if(this.isValidString(this.from) && 
          this.isValidString(this.to)) 
      {    
        switch(this.getMax(this.from, this.to, this.maxxi)) {
          case this.from:
            this.from = $("#from").val(this.maxxi);
            this.to = $("#to").val(this.maxxi);
            break;
          case this.to:
            this.to = $("#to").val(this.maxxi);
            break;
          case this.maxxi:
            break;
        }
        this.btn = true;
        this.clearAll();
      } else {
          $("#from, #to").css(this.errorStyle);
          this.btn = false;
          $(this.errorDiv).text("Inkorrekt tidsformat.");
      }
      checkBtn();
    }
   


    ,
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
      $("#from, #to").css(this.defaultStyle);
    },
    getBtn: function() {
      return this.btn;
    }
}



function searchInit() {
  tp = new TimePeriod();
  sf = new SubjectField();
  tp.init();
  sf.init();

}

function checkBtn() {
    console.log("TP: " + tp.getBtn() + "SF: " + sf.getBtn());
    if ((tp.getBtn() == true) && (sf.getBtn() == true)) {
      document.getElementById("btn").disabled = false;
    } else {
      document.getElementById("btn").disabled = true;
    }
}

function search() {
  charts[0].updateSubject(tp.from, tp.to, sf.sub);
}







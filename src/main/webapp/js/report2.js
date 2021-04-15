window.jsPDF = window.jspdf.jsPDF;

function registrationReport(){

var doc = new jsPDF();
  doc.autoTable({ html: '#registrationTable',
  useCss: true,})
  
  doc.setFontSize(16);
  doc.setTextColor('black');
  doc.text(20, 10.5, "Registrations");
  
  
		doc.setProperties({
        title: "table.pdf",
      });

      window.open(doc.output("bloburl", "_blank"));

}

function activeAccountsReport(){

var doc = new jsPDF();
  doc.autoTable({ html: '#activeAccountsTable',
  useCss: true,})
  
  doc.setFontSize(16);
  doc.setTextColor('black');
  doc.text(20, 10.5, "Active Accounts");
  
  
		doc.setProperties({
        title: "table.pdf",
      });

      window.open(doc.output("bloburl", "_blank"));

}

function inactiveAccountsReport(){

var doc = new jsPDF();
  doc.autoTable({ html: '#inactiveAccountsTable',
  useCss: true,})
  
  doc.setFontSize(16);
  doc.setTextColor('black');
  doc.text(20, 10.5, "Inactive Accounts");
  
  
		doc.setProperties({
        title: "table.pdf",
      });

      window.open(doc.output("bloburl", "_blank"));

}

function newAccountsReport(){

var doc = new jsPDF();
  doc.autoTable({ html: '#newAccountsTable',
  useCss: true,})
  
  doc.setFontSize(16);
  doc.setTextColor('black');
  doc.text(20, 10.5, "New Accounts");
  
  
		doc.setProperties({
        title: "table.pdf",
      });

      window.open(doc.output("bloburl", "_blank"));

}

function classListReport(){

var doc = new jsPDF();
  doc.autoTable({ html: '#classListTable',
  useCss: true,})
  
  doc.setFontSize(16);
  doc.setTextColor('black');
  doc.text(20, 10.5, "Class List");
  
  
		doc.setProperties({
        title: "table.pdf",
      });

      window.open(doc.output("bloburl", "_blank"));

}

function paymentReport(){

var doc = new jsPDF();
  doc.autoTable({ html: '#paymentTable',
  useCss: true,})
  
  doc.setFontSize(16);
  doc.setTextColor('black');
  doc.text(20, 10.5, "Payment List");
  
  
		doc.setProperties({
        title: "table.pdf",
      });

      window.open(doc.output("bloburl", "_blank"));

}
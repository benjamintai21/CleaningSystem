
function activate(element){
     let tabValue = parseInt(element.dataset.tab);
    
     let selectedcard = document.getElementById("card"+tabValue);
     let button = document.getElementById("arrow"+tabValue);
    if(selectedcard.classList.contains("none")){
        selectedcard.classList.remove("none");
        button.classList.remove("unactivated");
        button.classList.add("activated");
        
    }else if(!selectedcard.classList.contains("none")){
        selectedcard.classList.add("none");
        button.classList.remove("activated");
        button.classList.add("unactivated");
    }
}
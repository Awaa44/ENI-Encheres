document.getElementById("adressePrecedentes").addEventListener("change", function () {

    const select = document.getElementById("adressePrecedentes");
    if (!select) {
        console.error("Select adressePrecedentes introuvable");
        return;
    }

    const selectedOption = this.options[this.selectedIndex];

    document.getElementById("rueRetrait").value =
        selectedOption.dataset.rue || "";

    document.getElementById("cpRetrait").value =
        selectedOption.dataset.cp || "";

    document
        .getElementById("villeRetrait").value =
        selectedOption.dataset.ville || "";
});
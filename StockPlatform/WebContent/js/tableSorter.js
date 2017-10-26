'use strict'; 
$.fn.tableSorter = function (custSet) {
	if (this[0].tagName !== "TABLE") {                           // проверяем, получили ли мы таблицу.
		throw new Error("Элемент не является таблицей!");
	}
		
	var newColIndex; //номер столбца,  который только что получил клик
	var curColIndex; //номер столбца по которому была отсортирована таблица на момент клика	
	var sortWay = [ ]; //массив, где индекс соответствует индексу столбца, а булевское значение указывает направление 
                        //очередной сортировки в случае клика по th этого столбца, где true - по возрастанию.
	var rowsArr = [ ]; //массив строк таблицы, который мы каждый раз по клику сортируем.
	
	var options = {
		"sortFn": {},
		"allowSort": {}
	};
	
	for (var key = 0; key < this[0].tHead.rows[0].cells.length; key++){
		options.sortFn[key] = (key in custSet.custSortFn) ? custSet.custSortFn[key] : compareStr;
		options.allowSort[key] = (key in custSet.allowSorting) ? custSet.allowSorting[key] : true;
	}
	
	function compareStr(a, b) { //fn: функция сранения для строковых величин, используемая по умолчанию.
		if (a < b) {
			return -1; //a получает меньший индекс
		}
		if (a > b) {
			return 1;//a получает бoльший индекс
		}
		return 0;
		} 
		
	function changeIndexes() { 
		sortWay[newColIndex] = !sortWay[newColIndex];
		curColIndex = newColIndex;
	}
		
	function getSortWay(param) { //fn: вычисляет направление сортировки (1 или  -1)в зависимости от статуса столбца (sortWay[i]).
        return (param * 2) - 1; 
	}
		
	function getRowsArray(table) { //fn: делает из коллекции строк массив для сортировки .
		var rowsCollection = table.tBodies[0].rows; 
		for (var i = 0; i < rowsCollection.length; i++){
			rowsArr[i] = rowsCollection[i];
		}
	}
	
	function compareFn(a, b) { //fn: формирует функцию сравнения для сортировки массива из строк таблицы.
		var c = a.cells[newColIndex].textContent;  
		var d = b.cells[newColIndex].textContent;
		//определяем номерa ячеeк в сортируемых строках по которым будет идти сравнение.
		var result = options.sortFn[newColIndex](c, d);
		//выбираем функцию сравнения в зависимости от типа данных в столбце из объекта с настройками.
		return result*getSortWay(sortWay[newColIndex]); 
		//корректируем функцию сравнения с учётом направления сортировки.
	}
		
	function sortTable(table) { //fn: сортирует массив строк при помощи функции сравнения compareFn.
		var sortRowsArray = rowsArr.sort(compareFn); 
		renderSortedTable(sortRowsArray, table); 
	}
	
	function renderSortedTable(rows, table) { //fn: заполняет отсортированными строками в новом порядке таблицу.
		for (var i = 0; i < rows.length; i++){
				table.tBodies[0].appendChild(rows[i]);
			}
	}
	
	function cancelMarkSorted(table) { //fn: при сортировке по новому столбцу, удаляет отметку о сортировке (класс) с прежнего.   
		table.tHead.rows[0].cells[curColIndex].classList.remove("sortby-asc");
		table.tHead.rows[0].cells[curColIndex].classList.remove("sortby-desc");
	}
	
	function markSorted(table) { //fn: при сортировке по новому столбцу делает на нём отметку (меняет класс).
		if(sortWay[newColIndex]){ 
			table.tHead.rows[0].cells[newColIndex].classList.remove("sortby-desc");
			table.tHead.rows[0].cells[newColIndex].classList.add("sortby-asc");
		}else{
			table.tHead.rows[0].cells[newColIndex].classList.remove("sortby-asc");
			table.tHead.rows[0].cells[newColIndex].classList.add("sortby-desc");
		}
	}
	
	function initialisation(table) {  
		table.classList.add("sortable");
		for (var j = 0; j < table.tHead.rows[0].cells.length; j++){
			sortWay[j] = true;
			//задаём по умолчанию всем столбцам первую сортировку по возрастанию.
			table.tHead.rows[0].cells[j].classList.add("sort"); 
			//по умолчанию отмечаем все столбцы, как сортируемые.
		}
		newColIndex = 0;  
		//задаём индкс "стартового" столбца, по которому сортируем по умолчанию при загрузке. 
		for (var key in options.allowSort) {
			if(!options.allowSort[key]){
				table.tHead.rows[0].cells[key].classList.remove("sort"); 
				//удаляем отображение сортируемости у столбца, по которому сортировка недоступна.
                newColIndex = (newColIndex == key) ? newColIndex + 1 : newColIndex;	
				//в случае совпадения "несортируемого" со "стартовым" меняем индекс "стартового" столбца.
			}
		}
		getRowsArray(table); //получаем массив строк.
		sortTable(table); // сортируем массив строк по [newColIndex] столбцу.
		changeIndexes();
		markSorted(table); //меняем символ направления сортровки в th.
	}
	
	function thOnClick(e) {  
		newColIndex = e.target.cellIndex;
		if(options.allowSort[newColIndex] === false) { 
			return;
		}
		var table = $(e.target).parents("table");
		sortTable(table[0]);
		cancelMarkSorted(table[0]);			
		changeIndexes();
		markSorted(table[0]);
	}
	initialisation(this[0]);
	this[0].tHead.addEventListener("click", thOnClick);
}
		
	

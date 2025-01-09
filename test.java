' VBScript to filter data in Excel and export the filtered results
Dim objExcel, objWorkbook, objSheet, objRange, objFilteredRange, objOutputWorkbook, objOutputSheet
Dim sourceFile, outputFile, filterColumn, filterCriteria

' Define file paths and settings
sourceFile = "C:\path\to\your\inputfile.xlsx" ' Replace with your input file path
outputFile = "C:\path\to\your\outputfile.xlsx" ' Replace with your desired output file path
filterColumn = 12 ' Column to filter (e.g., 12 for "Status" in your example)
filterCriteria = "Your Filter Criteria Here" ' Replace with your desired filter criteria

' Create Excel application object
Set objExcel = CreateObject("Excel.Application")
objExcel.Visible = False
objExcel.DisplayAlerts = False

' Open the workbook and select the first worksheet
Set objWorkbook = objExcel.Workbooks.Open(sourceFile)
Set objSheet = objWorkbook.Worksheets(1)

' Define the range to filter (Assumes data starts at A1)
Set objRange = objSheet.UsedRange

' Apply filter
objRange.AutoFilter filterColumn, filterCriteria

' Copy filtered data to a new workbook
Set objFilteredRange = objRange.SpecialCells(12, 2) ' Get visible cells (after filtering)
Set objOutputWorkbook = objExcel.Workbooks.Add
Set objOutputSheet = objOutputWorkbook.Worksheets(1)
objFilteredRange.Copy
objOutputSheet.Paste
objOutputSheet.Columns.AutoFit

' Save the filtered data to the output file
objOutputWorkbook.SaveAs outputFile

' Close workbooks and quit Excel
objWorkbook.Close False
objOutputWorkbook.Close False
objExcel.Quit

' Clean up
Set objFilteredRange = Nothing
Set objRange = Nothing
Set objSheet = Nothing
Set objWorkbook = Nothing
Set objOutputSheet = Nothing
Set objOutputWorkbook = Nothing
Set objExcel = Nothing

WScript.Echo "Filtering complete. Output saved to: " & outputFile

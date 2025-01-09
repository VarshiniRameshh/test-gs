' VBScript to filter data in Excel and save results to a text file
Dim objExcel, objWorkbook, objSheet, objRange, objFilteredRange, objFileSystem, objOutputFile
Dim sourceFile, outputFile, filterColumn, filterCriteria, cellValue, row, column

' Define file paths and settings
sourceFile = "C:\path\to\your\inputfile.xlsx" ' Replace with your input Excel file path
outputFile = "C:\path\to\your\outputfile.txt" ' Replace with your desired output text file path
filterColumn = 12 ' Column to filter (e.g., 12 for "Status" in your example)
filterCriteria = "Onboarded" ' Replace with your desired filter criteria (e.g., "Onboarded")

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

' Get filtered data range (excluding headers)
Set objFilteredRange = objSheet.UsedRange.SpecialCells(12, 2) ' Visible cells after filtering

' Create FileSystemObject and output file
Set objFileSystem = CreateObject("Scripting.FileSystemObject")
Set objOutputFile = objFileSystem.CreateTextFile(outputFile, True)

' Write filtered data to the text file
For Each row In objFilteredRange.Rows
    For Each column In row.Columns
        cellValue = column.Value
        If IsNull(cellValue) Then
            cellValue = ""
        End If
        objOutputFile.Write cellValue & vbTab ' Tab-separated values
    Next
    objOutputFile.WriteLine ' Move to the next line
Next

' Close the text file
objOutputFile.Close

' Close the workbook and quit Excel
objWorkbook.Close False
objExcel.Quit

' Clean up
Set objFilteredRange = Nothing
Set objRange = Nothing
Set objSheet = Nothing
Set objWorkbook = Nothing
Set objFileSystem = Nothing
Set objOutputFile = Nothing
Set objExcel = Nothing

WScript.Echo "Filtering complete. Output saved to: " & outputFile

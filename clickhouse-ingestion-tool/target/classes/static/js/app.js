function startTransfer() {
    const direction = document.getElementById('direction').value;
    const tableName = document.getElementById('tableName').value;
    const filePath = document.getElementById('filePath').value;
    const statusBox = document.getElementById('statusBox');

    statusBox.innerHTML = '<p class="text-warning">Transferring data... Please wait</p>';

    fetch('/ingest', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            direction: direction,
            tableName: tableName,
            filePath: filePath
        })
    })
    .then(response => {
        if (!response.ok) throw new Error("Failed to ingest data");
        return response.json();
    })
    .then(data => {
        statusBox.innerHTML = `
            <p class="text-success">✅ Transfer completed successfully!</p>
            <p>Direction: ${direction === 'chToFile' ? 'ClickHouse to File' : 'File to ClickHouse'}</p>
            <p>Table: ${tableName}</p>
            <p>File: ${filePath}</p>
            <p>Total Records: ${data.recordCount}</p>
        `;
    })
    .catch(error => {
        statusBox.innerHTML = <p class="text-danger">❌ Error: ${error.message}</p>;
    });
}
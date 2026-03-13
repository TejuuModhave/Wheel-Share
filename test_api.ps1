$ErrorActionPreference = "Stop"
$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession

try {
    Write-Host "Testing Registration..."
    $regBody = @{
        "uname" = "Owner"
        "uemail" = "owner@test.com"
        "upassword" = "pass"
        "unumber" = "123"
    }
    # Form post (x-www-form-urlencoded) is default when body is a hashtable
    Invoke-WebRequest -Uri "http://localhost:8085/addingUser" -Method POST -Body $regBody -WebSession $session -MaximumRedirection 0 | Out-Null
    Write-Host "Success! User registered."
} catch {
    # It might respond with 302 Found which throws an error if MaximumRedirection is 0
    if ($_.Exception.Response.StatusCode -eq 302) {
        Write-Host "Success! User registered (302 Redirect)."
    } else {
        Write-Host "Failed Registration: $($_.Exception.Message)"
    }
}

try {
    Write-Host "Testing Login..."
    Invoke-WebRequest -Uri "http://localhost:8085/userlogin?userEmail=owner@test.com&userPassword=pass" -Method GET -WebSession $session -MaximumRedirection 0 | Out-Null
    Write-Host "Success! Logged in."
} catch {
    if ($_.Exception.Response.StatusCode -eq 302) {
        Write-Host "Success! Logged in (302 Redirect)."
    } else {
        Write-Host "Failed Login: $($_.Exception.Message)"
    }
}

try {
    Write-Host "Testing Add Equipment..."
    $eqBody = @{
        "pname" = "Tractor"
        "pprice" = "1000"
        "pdescription" = "Testing"
    }
    Invoke-WebRequest -Uri "http://localhost:8085/addingEquipment" -Method POST -Body $eqBody -WebSession $session -MaximumRedirection 0 | Out-Null
    Write-Host "Success! Equipment added."
} catch {
    if ($_.Exception.Response.StatusCode -eq 302) {
        Write-Host "Success! Equipment added (302 Redirect)."
    } else {
        Write-Host "Failed Equipment: $($_.Exception.Message)"
        Write-Host "Response Body: $($_.Exception.Response.GetResponseStream() | %{ (New-Object IO.StreamReader($_)).ReadToEnd() })"
    }
}

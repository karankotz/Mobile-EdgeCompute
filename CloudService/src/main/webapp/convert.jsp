<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap unit converster Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        .converter-form {
            width: 340px;
            margin: 50px auto;
        }
        .converter-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        .converter-form h2 {
            margin: 0 0 15px;
        }
        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }
        .btn {
            font-size: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="converter-form">
        <form action = "convert" method = "GET">
            <h2 class="text-center">Unit Converter</h2>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Input number" required="required" name="input">
            </div>
            <div class="form-group">
                <label for="typeofconversion">Covertion options</label>
                <select id="typeofconversion" name="typeofconversion">
                    <option value="f2c">Farenheit To Celsius</option>
                    <option value="c2f"> Celsius To Farenheit</option>
                    <option value="l2g"> Litre To US Liquid Gallon</option>
                    <option value="g2l"> US Liquid Gallon To Liter</option>
                    <option value="k2m">Speed - Kilometer/hour to Miles/Hour</option>
                    <option value="m2k">Speed - Miles/hour to Kilometer/hour</option>
                    <option value="w2h">Time - Weeks to hour</option>
                </select>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block">Convert</button>
            </div>
        </form>
    </div>
</body>
</html>
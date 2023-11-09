# APPA 
IS442 G1-T7

## Installation Steps:

#### 1. Frontend Setup

```
cd frontend
npm install
```

Proceed with installation depending on your Maven setup:

* [With Maven Extension](#with-maven-extension-in-ide)
* [With MVN in OS](#with-mvn-in-os)

<br />

### Compiling the Java Files:

Run `cd backend\analytics-app` where the files `compile.sh` and `compile.bat` exists.

If you are on `Windows`, run `compile.bat`.
If you are on `Mac`, run `compile.sh`.

### With Maven extension in IDE:

#### 2. Backend Setup

> If below steps display an error, run `cd backend\analytics-app` before starting the steps.

<ol>
    <li>Open Maven hierarchy</li>
    <li>Right click on analytics-app</li>
    <li>Hover over "Run Maven Commands..."</li>
    <li>Click on "clean"</li>
    <li>Repeat 2-4. Click on "install"</li>
</ol>



#### 3. Run Project
**Frontend**

<p>Go to root directory (APPA-IS442-G1-T7)</p>

```
npm run frontend
```
<br />

**Backend**
1. Navigate to the file: src\main\java\com\example\analyticsapp\AnalyticsAppApplication.java
2. Run code

</br>

### With MVN in OS:
#### 2. Backend Setup

<p>Go to root directory (APPA-IS442-G1-T7) </p>

If there's an error, run `cd backend\analytics-app` first.
```
mvn clean install
```

#### 3. Run Project

```
// Go to root directory (APPA-IS442-G1-T7)
npm run app
```

# APPA 
IS442 G1-T7

## Installation Steps:

#### 1. Frontend Setup

```
cd frontend
npm install
```

### With Maven extension in IDE:

#### 2. Backend Setup

> If below steps display an error, run `cd to backend\analytics-app` before starting the steps.

<ol>
    <li>Open Maven hierarchy</li>
    <li>Right click on analytics-app</li>
    <li>Hover over "Run Maven Commands..."</li>
    <li>Click on "clean"</li>
    <li>Repeat 2-4. Click on "install"</li>
</ol>



#### 3. Run Project
**Frontend**
```
// Go to root directory (APPA-IS442-G1-T7)
npm run frontend
```

**Backend**
1. Navigate to the file: src\main\java\com\example\analyticsapp\AnalyticsAppApplication.java
2. Run code

### With MVN in OS:
#### 2. Backend Setup

```
// Go to root directory (APPA-IS442-G1-T7)
// If there's an error, run below 
cd backend\analytics-app

mvn clean install
```

#### 3. Run Project

```
// Go to root directory (APPA-IS442-G1-T7)
npm run app
```
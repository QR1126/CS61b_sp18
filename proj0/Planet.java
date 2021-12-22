public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet planet) {
        this.xxPos = planet.xxPos;
        this.yyPos = planet.yyPos;
        this.xxVel = planet.xxVel;
        this.yyVel = planet.yyVel;
        this.mass = planet.mass;
        this.imgFileName = planet.imgFileName;
    }

    public double calcDistance(Planet other) {
        double dx = this.xxPos - other.xxPos;
        double dy = this.yyPos - other.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet other) {
        double r = calcDistance(other);
        return G * this.mass * other.mass / (r * r);
    }

    public double calcForceExertedByX(Planet other) {
        double dis = calcDistance(other);
        double force = calcForceExertedBy(other);
        return (other.xxPos - this.xxPos) / dis * force;
    }

    public double calcForceExertedByY(Planet other) {
        double dis = calcDistance(other);
        double force = calcForceExertedBy(other);
        return (other.yyPos - this.yyPos) / dis * force;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netForceX = 0;
        for (Planet planet : allPlanets) {
            if (planet.equals(this)) {
                continue;
            }
            netForceX+=calcForceExertedByX(planet);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netForceY = 0;
        for (Planet planet : allPlanets) {
            if (planet.equals(this)) {
                continue;
            }
            netForceY+=calcForceExertedByY(planet);
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {
        double accX = fX / this.mass;
        double accY = fY / this.mass;
        double newXXVel = this.xxVel + accX * dt;
        double newYYVel = this.yyVel + accY * dt;
        this.xxVel = newXXVel;
        this.yyVel = newYYVel;
        this.xxPos = this.xxPos + xxVel * dt;
        this.yyPos = this.yyPos + yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}

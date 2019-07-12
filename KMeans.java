class Kmeans
{
    public static void main(String a[])
    {
        double[] lat = new double[8];
        double[] lon = new double[8];
        int k;

        k = 3;
        lat[0] = 12.9261;lon[0] = 77.6199;
        lat[1] = 12.8498;lon[1] = 77.6522;
        lat[2] = 12.8022;lon[2] = 77.7162;
        lat[3] = 12.9946;lon[3] = 77.6831;
        lat[4] = 12.9750;lon[4] = 77.7432;
        lat[5] = 13.0206;lon[5] = 77.6456;
        lat[6] = 13.0487;lon[6] = 77.5901;
        lat[7] = 12.9819;lon[7] = 77.6234;
    
        KMeansClustering();
    }

    public double getDistance(double x1, double x2, double y1, double y2)
    {
        int R = 6371;
        double dLat = Math.toRadians(y1 - x1);
        double dLon = Math.toRadians(y2 - x2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(x1)) * Math.cos(Math.toRadians(y1)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;

        return d;
    }

    public void KMeansClustering()
    {
        boolean flag = true;
        double[][] m = new double[k][2];
        double[][] dist = new double[k][lat.length];
        double[][] sum = new double[k][2];
        int[] n = new int[k];
        int[][] clusters = new int[k][lat.length];

        //Clustering highly depends on choosing starting midpoints
        for(int i=0;i<k;i++)
        {
            m[i][0] = (double)Math.round(lat[i] * 10000d) / 10000d;
            m[i][1] = (double)Math.round(lon[i] * 10000d) / 10000d;
        }

        do {
            for(int i=0;i<k;i++)
            {
                sum[i][0] = 0;
                sum[i][1] = 0;
                n[i] = 0;
                for(int j=0;j<lat.length;j++)
                {
                    clusters[i][j] = -1;
                }
            }
            for(int i=0;i<lat.length;i++)
            {
                int min = 0;
                double minVal = dist[0][i];
                for(int j=0;j<k;j++)
                {
                    dist[j][i] = getDistance(m[j][0], m[j][1], lat[i], lon[i]);
                    if(dist[j][i] <= minVal)
                    {
                        minVal = dist[j][i];
                        min = j;
                    }
                }
                clusters[min][n[min]] = i;
                n[min]++;
                sum[min][0] += lat[i];
                sum[min][1] += lon[i];
            }

            boolean tempt = false;
            for(int i=0;i<k;i++)
            {
                sum[i][0] /= n[i];
                sum[i][1] /= n[i];

                sum[i][0] = (double)Math.round(sum[i][0] * 10000d) / 10000d;
                sum[i][1] = (double)Math.round(sum[i][1] * 10000d) / 10000d;

                if(sum[i][0] == m[i][0] && sum[i][1] == m[i][1])
                {

                }
                else
                {
                    m[i][0] = sum[i][0];
                    m[i][1] = sum[i][1];
                    tempt = true;
                }
            }
            if(!tempt)
                flag = false;
        }while(flag);

        //print clusters
    }
}
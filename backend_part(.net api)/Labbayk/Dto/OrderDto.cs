using Labbayk.Models;

namespace Labbayk.Dto
{
    public class OrderDto
    {

        public string CustomerName { get; set; } = string.Empty;
        public string CustomerPhone { get; set; }
        public double Total { get; set; }
    }
}

using System.ComponentModel.DataAnnotations;

namespace Labbayk.Models
{
    public class Order
    {
        [Key]
        public int Id { get; set; }
        public string CustomerName { get; set; } = string.Empty;
        public string CustomerPhone { get; set; }
        public double Total { get; set; }
        public Driver Driver { get; set; }
        public ICollection<OrderItem> OrderItems { get; set; }
    }
}
